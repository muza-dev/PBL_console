from database import get_db_connection

def show_cart():
    conn = get_db_connection()
    cursor = conn.cursor()
    print("🛒 Savatchadagi mahsulotlar:")
    # Add your logic to show items in the cart here
    conn.close()

def show_cart(user_email):
    """Foydalanuvchi savatchasidagi mahsulotlarni chiqarish"""
    conn = get_db_connection()
    cursor = conn.cursor()

    cursor.execute("""
    SELECT products.name, cart.quantity, cart.total_price
    FROM cart
    JOIN products ON cart.product_id = products.id
    WHERE cart.user_email = ?
    """, (user_email,))

    cart_items = cursor.fetchall()

    if not cart_items:
        print("🛒 Savatcha bo‘sh!")
    else:
        total_price = sum(item["total_price"] for item in cart_items)
        print("\n🛒 Savatchadagi mahsulotlar:")
        for item in cart_items:
            print(f"{item['quantity']} dona {item['name']} - ${item['total_price']}")
        print(f"\n🛍 Umumiy summa: ${total_price}")

    conn.close()

def add_to_cart(user_email):
    conn = get_db_connection()
    cursor = conn.cursor()
    print(f"➕ Savatchaga mahsulot qo'shish: {user_email}")
    # Add your logic to add items to the cart here
    conn.close()

def add_to_cart(user_email):
    """Foydalanuvchi savatiga mahsulot qo‘shish"""
    conn = get_db_connection()
    cursor = conn.cursor()

    # Mahsulotlar ro‘yxatini chiqarish
    cursor.execute("SELECT id, name, price, quantity FROM products")
    products = cursor.fetchall()

    print("\n📋 Mahsulotlar:")
    for product in products:
        print(f"{product['id']}) {product['name']} - ${product['price']} ({product['quantity']} dona mavjud)")

    # Mahsulot ID tanlash
    product_id = input("\n🛒 Savatchaga qo‘shish uchun mahsulot ID sini kiriting: ").strip()
    cursor.execute("SELECT name, price, quantity FROM products WHERE id = ?", (product_id,))
    product = cursor.fetchone()

    if not product:
        print("❌ Xatolik: Bunday mahsulot mavjud emas!")
        return

    max_quantity = product["quantity"]
    quantity = int(input(f"📦 Nechta {product['name']} qo‘shmoqchisiz? (Max: {max_quantity}): ").strip())

    if quantity < 1 or quantity > max_quantity:
        print("❌ Xatolik: Noto‘g‘ri miqdor!")
        return

    total_price = quantity * product["price"]

    # Savatchaga qo‘shish
    cursor.execute("""
    INSERT INTO cart (user_email, product_id, quantity, total_price) 
    VALUES (?, ?, ?, ?)
    """, (user_email, product_id, quantity, total_price))

    conn.commit()
    conn.close()
    print(f"✅ {quantity} dona {product['name']} savatchaga qo‘shildi!")

