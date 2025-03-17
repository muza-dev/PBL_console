import auth
import products
import cart
import orders
from database import initialize_database

# **Bazani ishga tushirish**
initialize_database()

def main():
    print("\n📌 Konsol E-Commerce Tizimi")
    print("1️⃣ Ro‘yxatdan o‘tish")
    print("2️⃣ Tizimga kirish")
    choice = input("Tanlang (1-2): ").strip()

    user = None
    is_admin = False
    if choice == "1":
        user = auth.register_user()
    elif choice == "2":
        user, is_admin = auth.login()
    else:
        print("❌ Noto‘g‘ri tanlov.")
        return

    if user:
        while True:
            print("\n📌 Bosh menyu")
            print("1️⃣ Mahsulotlar")
            print("2️⃣ Savatchani ko‘rish")
            print("3️⃣ Savatchaga qo‘shish")
            print("4️⃣ Buyurtmani tasdiqlash")
            if is_admin:
                print("5️⃣ Yangi mahsulot qo‘shish")
                print("6️⃣ Mahsulot o‘chirish")
            print("7️⃣ Chiqish")

            choice = input("Tanlang (1-7): ").strip()

            if choice == "1":
                from products import show_products
                show_products()
            elif choice == "2":
                from cart import show_cart
                show_cart(user)
            elif choice == "3":
                cart.add_to_cart(user)
            elif choice == "4":
                orders.checkout(user)
            elif is_admin and choice == "5":
                from products import add_product
                add_product()
            elif is_admin and choice == "6":
                from products import remove_product
                remove_product()
            elif choice == "7":
                print("\n👋 Rahmat! Dastur yakunlandi.")
                break
            else:
                print("❌ Xato! Qayta tanlang.")

if __name__ == "__main__":
    main()
