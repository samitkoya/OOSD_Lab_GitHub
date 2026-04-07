package hotel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    private HotelManager hotelManager = new HotelManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Management System");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab roomTab = new Tab("Room Management", buildRoomTab());
        Tab customerTab = new Tab("Customer Management", buildCustomerTab());
        Tab bookingTab = new Tab("Booking & Checkout", buildBookingTab());

        tabPane.getTabs().addAll(roomTab, customerTab, bookingTab);

        // Header
        Label title = new Label("🏨 Hotel Management System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);
        title.setPadding(new Insets(12, 20, 12, 20));

        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #2c3e50;");

        VBox root = new VBox(header, tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 850, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    // ─────────────────────────── ROOM TAB ───────────────────────────
    private VBox buildRoomTab() {
        // Form
        Label lNum = new Label("Room Number:");
        TextField tfNum = new TextField();
        tfNum.setPromptText("e.g. 101");

        Label lType = new Label("Room Type:");
        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("Single", "Double", "Deluxe", "Suite");
        cbType.setPromptText("Select type");

        Label lPrice = new Label("Price per Day (₹):");
        TextField tfPrice = new TextField();
        tfPrice.setPromptText("e.g. 2500");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));
        form.addRow(0, lNum, tfNum);
        form.addRow(1, lType, cbType);
        form.addRow(2, lPrice, tfPrice);

        Button btnAdd = new Button("➕ Add Room");
        Button btnShowAll = new Button("📋 All Rooms");
        Button btnShowAvail = new Button("✅ Available Rooms");

        HBox buttons = new HBox(10, btnAdd, btnShowAll, btnShowAvail);
        buttons.setPadding(new Insets(0, 15, 10, 15));

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(300);
        output.setStyle("-fx-font-family: monospace;");

        Label status = new Label();
        status.setPadding(new Insets(5, 15, 5, 15));

        // Events
        btnAdd.setOnAction(e -> {
            String num = tfNum.getText().trim();
            String type = cbType.getValue();
            String priceStr = tfPrice.getText().trim();
            if (num.isEmpty() || type == null || priceStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields.");
                return;
            }
            try {
                int roomNum = Integer.parseInt(num);
                double price = Double.parseDouble(priceStr);
                String msg = hotelManager.addRoom(roomNum, type, price);
                status.setTextFill(Color.DARKGREEN);
                status.setText(msg);
                tfNum.clear(); cbType.setValue(null); tfPrice.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Room number and price must be numeric.");
            }
        });

        btnShowAll.setOnAction(e -> output.setText(hotelManager.getAllRooms()));
        btnShowAvail.setOnAction(e -> output.setText(hotelManager.getAvailableRooms()));

        Label heading = sectionLabel("Add New Room");
        VBox tab = new VBox(10, heading, form, buttons, status, output);
        tab.setPadding(new Insets(10));
        return tab;
    }

    // ─────────────────────────── CUSTOMER TAB ───────────────────────────
    private VBox buildCustomerTab() {
        Label lId = new Label("Customer ID:");
        TextField tfId = new TextField();
        tfId.setPromptText("e.g. C001");

        Label lName = new Label("Name:");
        TextField tfName = new TextField();
        tfName.setPromptText("Full name");

        Label lContact = new Label("Contact Number:");
        TextField tfContact = new TextField();
        tfContact.setPromptText("10-digit number");

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10);
        form.setPadding(new Insets(15));
        form.addRow(0, lId, tfId);
        form.addRow(1, lName, tfName);
        form.addRow(2, lContact, tfContact);

        Button btnAdd = new Button("➕ Add Customer");
        Button btnView = new Button("📋 View All Customers");

        Label lRemId = new Label("Remove Customer ID:");
        TextField tfRemId = new TextField();
        tfRemId.setPromptText("Customer ID to remove");
        Button btnRemove = new Button("🗑 Remove Customer");

        HBox addButtons = new HBox(10, btnAdd, btnView);
        addButtons.setPadding(new Insets(0, 15, 5, 15));

        HBox removeRow = new HBox(10, lRemId, tfRemId, btnRemove);
        removeRow.setPadding(new Insets(0, 15, 5, 15));
        removeRow.setAlignment(Pos.CENTER_LEFT);

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(250);
        output.setStyle("-fx-font-family: monospace;");

        Label status = new Label();
        status.setPadding(new Insets(5, 15, 0, 15));

        btnAdd.setOnAction(e -> {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            String contact = tfContact.getText().trim();
            if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields.");
                return;
            }
            String msg = hotelManager.addCustomer(id, name, contact);
            status.setTextFill(Color.DARKGREEN);
            status.setText(msg);
            tfId.clear(); tfName.clear(); tfContact.clear();
        });

        btnView.setOnAction(e -> output.setText(hotelManager.getAllCustomers()));

        btnRemove.setOnAction(e -> {
            String id = tfRemId.getText().trim();
            if (id.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Enter a Customer ID to remove.");
                return;
            }
            String msg = hotelManager.removeCustomer(id);
            status.setTextFill(msg.startsWith("Error") ? Color.RED : Color.DARKGREEN);
            status.setText(msg);
            tfRemId.clear();
            output.setText(hotelManager.getAllCustomers());
        });

        Label heading = sectionLabel("Customer Records");
        VBox tab = new VBox(10, heading, form, addButtons, removeRow, status, output);
        tab.setPadding(new Insets(10));
        return tab;
    }

    // ─────────────────────────── BOOKING TAB ───────────────────────────
    private VBox buildBookingTab() {
        // Booking section
        Label lCustId = new Label("Customer ID:");
        TextField tfCustId = new TextField();
        tfCustId.setPromptText("e.g. C001");

        Label lRoomNum = new Label("Room Number:");
        TextField tfRoomNum = new TextField();
        tfRoomNum.setPromptText("e.g. 101");

        Label lDays = new Label("Number of Days:");
        TextField tfDays = new TextField();
        tfDays.setPromptText("e.g. 3");

        GridPane bookForm = new GridPane();
        bookForm.setHgap(10); bookForm.setVgap(10);
        bookForm.setPadding(new Insets(10, 15, 5, 15));
        bookForm.addRow(0, lCustId, tfCustId);
        bookForm.addRow(1, lRoomNum, tfRoomNum);
        bookForm.addRow(2, lDays, tfDays);

        Button btnBook = new Button("🛎 Book Room");

        // Checkout section
        Separator sep = new Separator();
        sep.setPadding(new Insets(10, 0, 10, 0));

        Label lChkCustId = new Label("Customer ID:");
        TextField tfChkCustId = new TextField();
        tfChkCustId.setPromptText("Customer ID to checkout");

        HBox checkoutRow = new HBox(10, lChkCustId, tfChkCustId);
        checkoutRow.setPadding(new Insets(5, 15, 5, 15));
        checkoutRow.setAlignment(Pos.CENTER_LEFT);

        Button btnCheckout = new Button("🚪 Checkout");

        HBox bookBtns = new HBox(10, btnBook);
        bookBtns.setPadding(new Insets(0, 15, 5, 15));

        HBox checkBtns = new HBox(10, btnCheckout);
        checkBtns.setPadding(new Insets(0, 15, 5, 15));

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(200);
        output.setStyle("-fx-font-family: monospace;");

        Label status = new Label();
        status.setPadding(new Insets(5, 15, 0, 15));

        btnBook.setOnAction(e -> {
            String custId = tfCustId.getText().trim();
            String roomStr = tfRoomNum.getText().trim();
            String daysStr = tfDays.getText().trim();
            if (custId.isEmpty() || roomStr.isEmpty() || daysStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields.");
                return;
            }
            try {
                int roomNum = Integer.parseInt(roomStr);
                int days = Integer.parseInt(daysStr);
                String msg = hotelManager.bookRoom(custId, roomNum, days);
                boolean success = !msg.startsWith("Error");
                status.setTextFill(success ? Color.DARKGREEN : Color.RED);
                status.setText(msg);
                if (success) { tfCustId.clear(); tfRoomNum.clear(); tfDays.clear(); }
                output.setText(hotelManager.getBookingSummary());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Room number and days must be numeric.");
            }
        });

        btnCheckout.setOnAction(e -> {
            String custId = tfChkCustId.getText().trim();
            if (custId.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Enter Customer ID to checkout.");
                return;
            }
            String msg = hotelManager.checkout(custId);
            boolean success = !msg.startsWith("Error");
            status.setTextFill(success ? Color.DARKGREEN : Color.RED);
            status.setText(msg);
            if (success) tfChkCustId.clear();
            output.setText(hotelManager.getBookingSummary());
        });

        Label bookHeading = sectionLabel("Book a Room");
        Label checkHeading = sectionLabel("Checkout Customer");

        VBox tab = new VBox(5,
                bookHeading, bookForm, bookBtns,
                sep,
                checkHeading, checkoutRow, checkBtns,
                status, output);
        tab.setPadding(new Insets(10));
        return tab;
    }

    private Label sectionLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        l.setTextFill(Color.web("#2c3e50"));
        l.setPadding(new Insets(5, 0, 0, 5));
        return l;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
