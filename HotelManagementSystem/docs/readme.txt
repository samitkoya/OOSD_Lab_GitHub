📁 File Structure
HotelManagement/
└── src/
    └── hotel/
        ├── Main.java          ← JavaFX UI (Stage, Scene, Tabs, Controls)
        ├── HotelManager.java  ← Backend logic (ArrayList, HashMap, Iterator, Collections.sort)
        ├── Room.java          ← Room model
        ├── Customer.java      ← Customer model
        └── Booking.java       ← Booking model

🚀 Step-by-Step Setup in Eclipse
Step 1 — Install JavaFX SDK

Download JavaFX SDK from: https://gluonhq.com/products/javafx/ (choose your OS, version 17 or 21)
Extract it somewhere like C:\javafx-sdk-21\ (Windows) or /home/you/javafx-sdk-21/ (Linux/Mac)


Step 2 — Create Project in Eclipse

Open Eclipse → File → New → Java Project
Name it HotelManagement → Finish
Inside the project, open src/ → create a package named hotel
Copy all 5 .java files into the hotel package (paste inside src/hotel/ folder)


Step 3 — Add JavaFX Library to Eclipse

Right-click project → Build Path → Configure Build Path
Go to Libraries tab → Classpath → click Add External JARs
Navigate to your JavaFX SDK's lib/ folder and select all .jar files there
Click Apply and Close


Step 4 — Add VM Arguments (IMPORTANT)

Right-click Main.java → Run As → Run Configurations
Click the Arguments tab → in VM arguments box, paste:

--module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
(Replace the path with YOUR JavaFX SDK lib folder path)

Click Apply → Run


Step 5 — Run!
Right-click Main.java → Run As → Java Application (use the Run Configuration you just set)

🖥️ What the App Does
TabFeaturesRoom ManagementAdd rooms, view all (sorted by price), view available onlyCustomer ManagementAdd customers, view all, remove customersBooking & CheckoutBook room (auto-marks occupied, calculates bill), checkout (releases room)
Collections used (as required by lab):

ArrayList — stores rooms and customers
HashMap<Integer, Booking> — maps room number → booking
Iterator — used for traversal and safe removal
Collections.sort() — sorts rooms by price (Room implements Comparable)


FOR VSCODE -
## Fix — Use This Exact Command in PowerShell

### Step 1 — Find your JavaFX path
Open File Explorer and find where you extracted JavaFX. It'll look like:
```
C:\javafx-sdk-21.0.7\lib
```
Check that folder has `.jar` files inside it.

---

### Step 2 — Run these commands one by one

First, go back to your project root (not inside `hotel` folder):
```powershell
cd "C:\Users\Samit Reddy\Downloads\HME"
```

Then **compile** (replace the JavaFX path with yours):
```powershell
javac --module-path "C:\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -d out src\hotel\*.java
```

Then **run**:
```powershell
java --module-path "C:\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -cp out hotel.Main
```

---

### If you don't know your JavaFX path, run this to find it:
```powershell
Get-ChildItem "C:\Users\Samit Reddy\Downloads\" -Filter "javafx*" -Recurse -Directory | Select-Object FullName
```

Paste the output here and I'll give you the exact command with the correct path filled in. 

Also tell me — did you download and extract the JavaFX SDK zip file already?