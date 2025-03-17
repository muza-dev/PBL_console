from database import get_db_connection

def checkout(user_email):
    """Foydalanuvchi buyurtma berishi uchun funksiya"""
    conn = get_db_connection()
    cursor = conn.cursor()

    # **Mahsulotlar ro‘yxatini chiqarish**
    cursor.execute("SELECT id, name, price, quantity FROM products")
    products = cursor.fetchall()

    if not products:
        print("📭 Hozircha mahsulotlar mavjud emas!")
        conn.close()
        return

    print("\n📋 Mahsulotlar:")
    for product in products:
        print(f"{product['id']}) {product['name']} - ${product['price']} ({product['quantity']} dona mavjud)")

    # **Buyurtma berish uchun mahsulot tanlash**
    product_id = input("\n🛍 Buyurtma bermoqchi bo‘lgan mahsulotingizni ID sini kiriting: ").strip()

    # **Mahsulot mavjudligini tekshirish**
    cursor.execute("SELECT name, price, quantity FROM products WHERE id = ?", (product_id,))
    product = cursor.fetchone()

    if not product:
        print("❌ Xatolik: Bunday mahsulot mavjud emas!")
        conn.close()
        return

    max_quantity = product["quantity"]
    quantity = int(input(f"📦 Nechta {product['name']} buyurtma bermoqchisiz? (Max: {max_quantity}): ").strip())

    if quantity < 1 or quantity > max_quantity:
        print("❌ Xatolik: Noto‘g‘ri miqdor!")
        conn.close()
        return

    total_price = quantity * product["price"]

    # **Buyurtmani tasdiqlash**
    confirm = input(f"\n📌 Buyurtma berishni tasdiqlaysizmi? (ha/yo'q): ").strip().lower()
    if confirm != "ha":
        print("❌ Buyurtma bekor qilindi.")
        conn.close()
        return

    # **Telefon raqami va yashash manzilini so‘rash**
    phone_number = input("📞 Telefon raqamingizni kiriting: ").strip()
    address = input("🏠 Yashash manzilingizni kiriting: ").strip()

    # **Buyurtmani bazaga yozish**
    cursor.execute("""
    INSERT INTO orders (user_email, order_date, total_price, status)
    VALUES (?, CURRENT_TIMESTAMP, ?, 'tasdiqlangan')
    """, (user_email, total_price))

    # **Ombordagi mahsulot sonini kamaytirish**
    cursor.execute("UPDATE products SET quantity = quantity - ? WHERE id = ?", (quantity, product_id))

    conn.commit()
    conn.close()

    print(f"\n✅ Buyurtmangiz qabul qilindi! Mahsulot: {product['name']} - {quantity} dona, Jami summa: ${total_price}")
    print(f"📦 Yetkazib berish manzili: {address}, Tel: {phone_number}")

    # **To‘lov va Yetkazish haqida xabar**
    print(f"\n🚛 Kuryerlarimiz tez orada sizga aloqaga chiqishadi!")
    print(f"💰 To‘lov naqd ko‘rinishda amalga oshiriladi!")
