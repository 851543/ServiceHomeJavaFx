package com.token.controller;

import com.gn.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BookLendViewCtrl {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField bookNameField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField userNameField;

    private Stage stage;

//    //���ĵ���
//    private Book book;
//
//    //������
//    private User user;
//
//    private LendService lendService = new LendServiceImpl();
//
//    private TableView<Book> bookTableView;
//
//
//    @FXML
//    private void closeView() {
//        stage.close();
//    }
//
//    @FXML
//    private void add() {
//        lendService.add(Integer.parseInt(bookIdField.getText()), Integer.parseInt(userIdField.getText()));
//        book.setStatus(Constant.STATUS_LEND);
//        user.setLend(true);
//        //ˢ��ͼ���ѯ�����е�����
//        bookTableView.refresh();
//
//        stage.close();
//    }
//
//    /*
//        ��ʼ�������û�ѡ���stage
//    */
//    @FXML
//    private void initSelectUserStage() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(App.class.getResource("/com/bjpowernode/module/user/UserSelectView.fxml"));
//        StackPane target = (StackPane) loader.load();
//        Scene scene = new Scene(target);
//
//        Stage stage = new Stage();//������̨��
//        UserSelectViewCtrl controller = (UserSelectViewCtrl)loader.getController();
//        controller.setStage(stage);
//        controller.setBookLendViewCtrl(this);
//        stage.setHeight(800);
//        stage.setWidth(700);
//        //���ô���ͼ��
//        stage.getIcons().add(new Image("icon.png"));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(scene); //������������̨��
//        stage.show(); //��ʾ���ڣ�
//    }
//
//    public TableView<Book> getBookTableView() {
//        return bookTableView;
//    }
//
//    public void setBookTableView(TableView<Book> bookTableView) {
//        this.bookTableView = bookTableView;
//    }
//
//    public Stage getStage() {
//        return stage;
//    }
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//        bookIdField.setText(String.valueOf(book.getId()));
//        bookNameField.setText(book.getBookName());
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//        userIdField.setText(String.valueOf(user.getId()));
//        userNameField.setText(user.getName());
//    }
}
