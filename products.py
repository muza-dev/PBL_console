from database import get_db_connection

def show_products():
    """Barcha mahsulotlarni ekranga chiqarish"""
    conn = get_db_connection()
    cursor = conn.cursor()
    
    cursor.execute("SELECT id, name, price, quantity, category FROM products")
    products = cursor.fetchall()

    if not products:
        print("📭 Mahsulotlar mavjud emas!")
    else:
        print("\n📋 Mahsulotlar ro‘yxati:")
        for product in products:
            print(f"{product['id']}) {product['name']} - ${product['price']} "
                  f"({product['quantity']} dona, Kategoriya: {product['category']})")
    
    conn.close()
    

def add_product():
    """Administrator yangi mahsulot qo‘shishi uchun funksiya"""
    conn = get_db_connection()
    cursor = conn.cursor()

    print("\n🆕 Yangi mahsulot qo‘shish")
    name = input("📌 Mahsulot nomi: ").strip()
    description = input("📝 Tavsif: ").strip()
    
    try:
        price = float(input("💲 Narxi: ").strip())  # Narxni float ga aylantirish
        quantity = int(input("📦 Ombordagi soni: ").strip())  # Miqdorni int ga aylantirish
    except ValueError:
        print("❌ Xatolik: Narx yoki miqdor noto‘g‘ri kiritildi!")
        return

    category = input("🏷 Kategoriya: ").strip()

    # SQL orqali mahsulot qo‘shish
    try:
        cursor.execute("""
        INSERT INTO products (name, description, price, quantity, category)
        VALUES (?, ?, ?, ?, ?)
        """, (name, description, price, quantity, category))
        
        conn.commit()  # Yangi mahsulotni bazaga saqlash
        print(f"✅ Mahsulot '{name}' muvaffaqiyatli qo‘shildi!")
    except Exception as e:
        print(f"❌ Xatolik yuz berdi: {e}")
    
    conn.close()  # Bog‘lanishni yopish


def remove_product():
    """Administrator mahsulotni katalogdan o‘chirishi mumkin"""
    conn = get_db_connection()
    cursor = conn.cursor()

    # **Barcha mahsulotlarni chiqarish**
    cursor.execute("SELECT id, name, price, quantity FROM products")
    products = cursor.fetchall()

    if not products:
        print("📭 Katalog bo‘sh! O‘chirish uchun mahsulot mavjud emas.")
        conn.close()
        return

    print("\n🗑️ O‘chirish mumkin bo‘lgan mahsulotlar:")
    for product in products:
        print(f"{product['id']}) {product['name']} - ${product['price']} ({product['quantity']} dona)")

    # **O‘chiriladigan mahsulot ID sini tanlash**
    product_id = input("\n🗑️ O‘chirmoqchi bo‘lgan mahsulot ID sini kiriting: ").strip()

    # **Tanlangan ID mahsulot bazada bor yoki yo‘qligini tekshirish**
    cursor.execute("SELECT name FROM products WHERE id = ?", (product_id,))
    product = cursor.fetchone()

    if not product:
        print("❌ Xatolik: Bunday ID ga ega mahsulot mavjud emas!")
        conn.close()
        return

    # **Mahsulotni bazadan o‘chirish**
    cursor.execute("DELETE FROM products WHERE id = ?", (product_id,))
    conn.commit()

    print(f"✅ Mahsulot '{product['name']}' katalogdan o‘chirildi!")

    conn.close()
