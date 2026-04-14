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

        // Filter Section
        Label lFilterType = new Label("Filter Type:");
        ComboBox<String> cbFilterType = new ComboBox<>();
        cbFilterType.getItems().addAll("Any", "Single", "Double", "Deluxe", "Suite");
        cbFilterType.setValue("Any");

        TextField tfMin = new TextField(); tfMin.setPromptText("Min ₹"); tfMin.setPrefWidth(80);
        TextField tfMax = new TextField(); tfMax.setPromptText("Max ₹"); tfMax.setPrefWidth(80);
        Button btnFilter = new Button("🔍 Filter");

        HBox filterBox = new HBox(10, lFilterType, cbFilterType, tfMin, tfMax, btnFilter);
        filterBox.setPadding(new Insets(5, 15, 10, 15));
        filterBox.setAlignment(Pos.CENTER_LEFT);

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(250);
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
        
        btnFilter.setOnAction(e -> {
            String type = cbFilterType.getValue();
            if ("Any".equals(type)) type = "";
            try {
                double min = tfMin.getText().isEmpty() ? 0 : Double.parseDouble(tfMin.getText());
                double max = tfMax.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(tfMax.getText());
                output.setText(hotelManager.filterRooms(type, min, max));
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Prices must be numeric.");
            }
        });

        Label heading = sectionLabel("Add New Room");
        VBox tab = new VBox(5, heading, form, buttons, sectionLabel("Filter Inventory"), filterBox, status, output);
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

        Label lSearch = new Label("Search Name/ID:");
        TextField tfSearch = new TextField();
        tfSearch.setPromptText("Enter name or ID");
        Button btnSearch = new Button("🔍 Search");

        Label lRemId = new Label("Remove ID:");
        TextField tfRemId = new TextField();
        tfRemId.setPromptText("ID to remove");
        Button btnRemove = new Button("🗑 Remove");

        HBox addButtons = new HBox(10, btnAdd, btnView);
        addButtons.setPadding(new Insets(0, 15, 5, 15));

        HBox searchRow = new HBox(10, lSearch, tfSearch, btnSearch);
        searchRow.setPadding(new Insets(0, 15, 5, 15));
        searchRow.setAlignment(Pos.CENTER_LEFT);

        HBox removeRow = new HBox(10, lRemId, tfRemId, btnRemove);
        removeRow.setPadding(new Insets(0, 15, 5, 15));
        removeRow.setAlignment(Pos.CENTER_LEFT);

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(250);
        output.setStyle("-fx-font-family: monospace;");

        Label status = new Label();
        status.setPadding(new Insets(5, 15, 0, 15));

        // Events
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

        btnSearch.setOnAction(e -> {
            String query = tfSearch.getText().trim();
            if (query.isEmpty()) {
                output.setText(hotelManager.getAllCustomers());
            } else {
                output.setText(hotelManager.searchCustomers(query));
            }
        });

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
        VBox tab = new VBox(10, heading, form, addButtons, searchRow, removeRow, status, output);
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

        // Service section
        Separator sep1 = new Separator();
        sep1.setPadding(new Insets(5, 0, 5, 0));
        
        Label lSvcCustId = new Label("Customer ID:");
        TextField tfSvcCustId = new TextField();
        tfSvcCustId.setPromptText("Customer ID");
        
        ComboBox<String> cbSvc = new ComboBox<>();
        cbSvc.getItems().addAll("Room Service - ₹500", "Laundry - ₹300", "Spa - ₹1500");
        cbSvc.setPromptText("Select Service");
        
        Button btnAddSvc = new Button("➕ Add Service");

        HBox serviceRow = new HBox(10, lSvcCustId, tfSvcCustId, cbSvc, btnAddSvc);
        serviceRow.setPadding(new Insets(5, 15, 5, 15));
        serviceRow.setAlignment(Pos.CENTER_LEFT);

        // Checkout section
        Separator sep2 = new Separator();
        sep2.setPadding(new Insets(5, 0, 5, 0));

        Label lChkCustId = new Label("Customer ID:");
        TextField tfChkCustId = new TextField();
        tfChkCustId.setPromptText("ID to checkout");

        HBox checkoutRow = new HBox(10, lChkCustId, tfChkCustId);
        checkoutRow.setPadding(new Insets(5, 15, 5, 15));
        checkoutRow.setAlignment(Pos.CENTER_LEFT);

        Button btnCheckout = new Button("🚪 Checkout");

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(180);
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

        btnAddSvc.setOnAction(e -> {
            String custId = tfSvcCustId.getText().trim();
            String svcInfo = cbSvc.getValue();
            if (custId.isEmpty() || svcInfo == null) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Select customer and service.");
                return;
            }
            String svcName = svcInfo.split(" - ")[0];
            double cost = Double.parseDouble(svcInfo.split("₹")[1]);
            String msg = hotelManager.addServiceCharge(custId, svcName, cost);
            status.setTextFill(msg.startsWith("Error") ? Color.RED : Color.DARKGREEN);
            status.setText(msg);
            output.setText(hotelManager.getBookingSummary());
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

        VBox tab = new VBox(5,
                sectionLabel("Book a Room"), bookForm, new HBox(15, btnBook),
                sep1,
                sectionLabel("Extra Services"), serviceRow,
                sep2,
                sectionLabel("Checkout Customer"), checkoutRow, new HBox(15, btnCheckout),
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
