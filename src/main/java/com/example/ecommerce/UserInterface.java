package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface
{
    GridPane loginPage;

    Button signInButton;

    Label welcomeLabel;
    HBox headerBar;
    HBox footerBar;

    Customer loggedInCustomer;

    VBox body;

    ProductList productList = new ProductList();

//    OrderShowList orderShowList = new OrderShowList();

    ObservableList<Product> itemInCart = FXCollections.observableArrayList();

//    ObservableList<OrderShow> itemIn = FXCollections.observableArrayList();
    VBox productpage;

    Button placeOrderButton = new Button("Place Order");


    public BorderPane createContent()
    {
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
        root.setTop(headerBar);
//        root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productpage = productList.getAllProducts();
        body.getChildren().add(productpage);

        root.setBottom(footerBar);
        return root;
    }

   public UserInterface()
    {
        CreateLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void CreateLoginPage()
    {
        Text userNameText = new Text("User Name");
        Text passWordText  = new Text("Password");

        TextField userName = new TextField("tskulkarni123@gmail.com");
        userName.setPromptText("Enter your username");
        PasswordField password = new PasswordField();
        password.setText("tejas@123");
        password.setPromptText("Enter your password");
        Label messageLabel = new Label("Hii");

        //button
        Button loginbutton = new Button("Login");

        // now put this controls in gridpane
        loginPage = new GridPane();
//        loginPage.setStyle(" -fx-background-color:grey;");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passWordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(messageLabel,0,2);
        loginPage.add(loginbutton,1,2);

        loginbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name , pass);
                if(loggedInCustomer!=null)
                {
                    messageLabel.setText("Welcome : " + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome "+ loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productpage);
                }
                else
                {
                    messageLabel.setText("Login failed !!  please provide correct credentials");
                }
            }
        });
    }

    private  void createHeaderBar()
    {
        // home button
        Button homeButton = new Button();
        Image image = new Image("C:\\Users\\Tejas\\Ecommerce\\src\\ecomm.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(150);
        homeButton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Here");
        searchBar.setPrefWidth(280);

        //search button
        Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        // cart button here
        Button cartButton = new Button("Cart");




        headerBar = new HBox();
//        headerBar.setStyle("-fx-background-color:grey");
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();  // remove everything
                body.getChildren().add(loginPage); // put login page
                headerBar.getChildren().remove(signInButton);
            }
        });

        // check this
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemInCart); //   in this line check
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false); //  when we click it footer and all is invisible
            }
        });

//        orderbutton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                body.getChildren().clear();
//                VBox orderPage = orderShowList.getProductsInCart(itemIn);
//                orderPage.setAlignment(Pos.CENTER);
//                orderPage.setSpacing(10);
//                body.getChildren().add(orderPage);
//                footerBar.setVisible(false);
//            }
//        });


        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                // need list of product
                if(itemInCart==null)
                {
                    showDialogue("Please add some product in the cart to place order");
                    return;
                }
                if(loggedInCustomer==null)
                {
                    showDialogue("Please log in first to place order");
                    return;
                }

                int count = Order.placeMultipleOrder(loggedInCustomer,itemInCart);
                if(count!=0)
                {
                    showDialogue("Order for "+count+" product placed Successfully!!");
                }
                else
                {
                    showDialogue("Order failed");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productpage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1)
                {
                    headerBar.getChildren().add(signInButton);
                }
            }
        });



    }


    // footer
    private  void createFooterBar()
    {
        //buy now button
        Button buyNowButton = new Button("BuyNow");
        // add to cart button
        Button addToCartButton = new Button("Add To Cart");
        footerBar = new HBox();
//        headerBar.setStyle("-fx-background-color:grey");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);

        // adding all the button to the footerbar
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                 Product product = productList.getSelectedProduct();
                 if(product==null)
                 {
                    showDialogue("Please select a product first to place order!!");
                    return;
                 }
                 if(loggedInCustomer==null)
                 {
                     showDialogue("Please log in first to place order");
                     return;
                 }

                 boolean status = Order.placeOrder(loggedInCustomer,product);
                 if(status)
                 {
                     showDialogue("Order placed Successfully!!");
                 }
                 else
                 {
                     showDialogue("Order failed");
                 }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product==null)
                {
                    showDialogue("Please select a product first to add in cart!!");
                    return;
                }

                itemInCart.add(product);
                showDialogue("Select item has Successfully added to the cart");
            }
        });


    }

    private  void showDialogue(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }

}
