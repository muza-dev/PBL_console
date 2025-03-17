import sqlite3

DB_FILE = "ecommerce.db"

def get_db_connection():
    """SQLite bazasiga ulanish"""
    conn = sqlite3.connect(DB_FILE)
    conn.row_factory = sqlite3.Row  # Natijalarni dictionary sifatida olish
    return conn

def initialize_database():
    """Ma'lumotlar bazasini yaratish"""
    conn = get_db_connection()
    cursor = conn.cursor()
    
    # **Foydalanuvchilar jadvali**
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS users (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        username TEXT UNIQUE NOT NULL,
        email TEXT UNIQUE NOT NULL,
        password TEXT NOT NULL,
        is_admin BOOLEAN NOT NULL DEFAULT 0
    )
    """)

    # **Mahsulotlar jadvali**
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS products (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        description TEXT,
        price REAL NOT NULL,
        quantity INTEGER NOT NULL,
        category TEXT NOT NULL
    )
    """)

    # **Savatcha jadvali**
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS cart (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        user_email TEXT NOT NULL,
        product_id INTEGER NOT NULL,
        quantity INTEGER NOT NULL,
        total_price REAL NOT NULL,
        FOREIGN KEY (user_email) REFERENCES users(email),
        FOREIGN KEY (product_id) REFERENCES products(id)
    )
    """)

    # **Buyurtmalar jadvali**
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS orders (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        user_email TEXT NOT NULL,
        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        total_price REAL NOT NULL,
        status TEXT NOT NULL CHECK(status IN ('tasdiqlangan', 'bekor qilingan')),
        FOREIGN KEY (user_email) REFERENCES users(email)
    )
    """)

    conn.commit()
    conn.close()

def add_sample_products():
    """Bazaga namunaviy mahsulotlarni qo‘shish"""
    conn = get_db_connection()
    cursor = conn.cursor()

    sample_products = [
        ("Noutbuk", "Yangi model, 16GB RAM, SSD 512GB", 899.99, 10, "Elektronika"),
        ("Smartfon", "Flagship model, 128GB xotira, OLED ekran", 599.99, 15, "Elektronika"),
        ("Quloqchin", "Simli quloqchin, yuqori ovoz sifati", 49.99, 30, "Aksessuar"),
        ("Klaviatura", "Mexanik klaviatura, RGB yoritish", 79.99, 20, "Kompyuter texnikasi"),
        ("Sichqoncha", "O‘yin uchun sichqoncha, 7 ta tugma", 39.99, 25, "Kompyuter texnikasi"),
        ("Televizor", "55 dyuym, 4K UHD, Smart TV", 1099.99, 5, "Elektronika"),
        ("Kofe mashinasi", "Avtomatik espresso mashinasi", 199.99, 8, "Maishiy texnika"),
        ("Dazmol", "Bug‘li dazmol, tez qiziydi", 49.99, 15, "Maishiy texnika"),
        ("Elektron soat", "Aqlli soat, yurak urishini o‘lchash", 199.99, 12, "Aksessuar"),
        ("Portativ quvvatlagich", "10000mAh, tez quvvatlash", 29.99, 20, "Aksessuar")
    ]

    cursor.executemany("""
    INSERT INTO products (name, description, price, quantity, category) 
    VALUES (?, ?, ?, ?, ?)
    """, sample_products)

    conn.commit()
    conn.close()
    print("✅ Namunaviy mahsulotlar bazaga qo‘shildi!")
