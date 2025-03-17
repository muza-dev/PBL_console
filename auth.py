import bcrypt # type: ignore
from database import get_db_connection, initialize_database

# **Admin login va parol**
ADMIN_LOGIN = "admin"
ADMIN_PASSWORD = "admin123"

# **Admin parolini hash qilish**
ADMIN_PASSWORD_HASH = bcrypt.hashpw(ADMIN_PASSWORD.encode(), bcrypt.gensalt()).decode()

def hash_password(password):
    """Parolni hash qilish"""
    return bcrypt.hashpw(password.encode(), bcrypt.gensalt()).decode()

def verify_password(input_password, stored_password_hash):
    """Kiritilgan parolni bazadagi hashlangan parol bilan solishtirish"""
    return bcrypt.checkpw(input_password.encode(), stored_password_hash.encode())

def register_user():
    """Foydalanuvchini ro‘yxatdan o‘tkazish"""
    conn = get_db_connection()
    cursor = conn.cursor()
    
    username = input("Login kiriting: ").strip()
    
    # Admin loginidan foydalanish mumkin emas
    if username.lower() == ADMIN_LOGIN:
        print("❌ Bu login admin uchun rezerv qilingan!")
        return None

    # Foydalanuvchini tekshirish
    cursor.execute("SELECT id FROM users WHERE username = ?", (username,))
    if cursor.fetchone():
        print("❌ Bu login allaqachon mavjud!")
        return None
    
    email = input("Email kiriting: ").strip()
    password = input("Parol kiriting (kamida 8 ta belgi): ").strip()
    
    if len(password) < 8:
        print("❌ Parol kamida 8 ta belgidan iborat bo‘lishi kerak!")
        return None

    # Parolni hash qilish
    hashed_password = hash_password(password)
    
    # Foydalanuvchini bazaga yozish
    cursor.execute("INSERT INTO users (username, email, password, is_admin) VALUES (?, ?, ?, ?)", 
                   (username, email, hashed_password, 0))
    conn.commit()
    conn.close()

    print("✅ Ro‘yxatdan o‘tish muvaffaqiyatli yakunlandi!")
    return username

def login():
    """Tizimga kirish"""
    conn = get_db_connection()
    cursor = conn.cursor()

    username = input("Login kiriting: ").strip()
    password = input("Parol kiriting: ").strip()

    # **Admin loginni tekshirish**
    if username.lower() == ADMIN_LOGIN and verify_password(password, ADMIN_PASSWORD_HASH):
        print(f"✅ Xush kelibsiz, Admin!")
        return username, True  # Admin huquqlari bilan tizimga kiradi

    # **Oddiy foydalanuvchini tekshirish**
    cursor.execute("SELECT password, is_admin FROM users WHERE username = ?", (username,))
    user = cursor.fetchone()
    
    if user and verify_password(password, user["password"]):
        print(f"✅ Xush kelibsiz, {username}!")
        return username, bool(user["is_admin"])

    print("❌ Login yoki parol noto‘g‘ri!")
    return None, None
