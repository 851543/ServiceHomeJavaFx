
package com.token.controller;

import com.gn.App;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ͼ�����
 *
 * @author admin
 */
@Component
public class BookViewCtrl implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    @FXML
//    private TableView<Book> bookTableView;
//    @FXML
//    private TableColumn<Book, String> c1;
//    @FXML
//    private TableColumn<Book, String> c2;
//    @FXML
//    private TableColumn<Book, String> c3;
//    @FXML
//    private TableColumn<Book, String> c4;
//    @FXML
//    private TableColumn<Book, String> c5;
//    @FXML
//    private TableColumn<Book, String> c6;
//    @FXML
//    private TableColumn<Book, String> c7;
//
//    @FXML
//    private TextField bookNameField;
//
//    @FXML
//    private TextField isbnField;
//
//    ObservableList<Book> books = FXCollections.observableArrayList();
//
//    private BookService bookService = new BookServiceImpl();
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
///*
//        books.add(new Book(1, "javaʵս����", "����", Constant.TYPE_COMPUTER, "12-987", "XX������", Constant.STATUS_STORAGE));
//        books.add(new Book(2, "���֮��", "����", Constant.TYPE_COMPUTER, "1245-987", "XX������", Constant.STATUS_STORAGE));
//        books.add(new Book(3, "��׵������ָ��", "����", Constant.TYPE_COMPUTER, "08712-987", "XX������", Constant.STATUS_STORAGE));
//*/
//        //��ѯ
//        List<Book> bookList = bookService.select(null);
//        books.addAll(bookList);
//
//        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
//        c2.setCellValueFactory(new PropertyValueFactory<>("bookName"));
//        c3.setCellValueFactory(new PropertyValueFactory<>("author"));
//        c4.setCellValueFactory(new PropertyValueFactory<>("type"));
//        c5.setCellValueFactory(new PropertyValueFactory<>("isbn"));
//        c6.setCellValueFactory(new PropertyValueFactory<>("publisher"));
//        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
//        bookTableView.setItems(books);
//
//    }
//
//    /*
//        ����
//     */
//    @FXML
//    private void lendBook() {
//        try {
//            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
//            if (book == null || Constant.STATUS_LEND.equals(book.getStatus())){
//                Alerts.warning("δѡ��","��ѡ��ɽ��ĵ��鼮");
//                return;
//            }
//
//            initLendStage(book);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void deleteBook() {
//        try {
//            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
//            if (book == null){
//                Alerts.warning("δѡ��","����ѡ��Ҫɾ�����鼮");
//                return;
//            }
//            bookService.delete(book.getId());
//            this.books.remove(book);
//            Alerts.success("�ɹ�", "ͼ��ɾ���ɹ�");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Alerts.error("ʧ��","ͼ��ɾ��ʧ��");
//        }
//    }
//
//    /*
//        ��ѯ
//     */
//    @FXML
//    private void bookSelect(){
//        //��ȡ�û������������isbn
//        String bookName = bookNameField.getText();
//        String isbn = isbnField.getText();
//        boolean bookFlag = "".equals(bookName);
//        boolean isbnFlag = "".equals(isbn);
//
//        Book book = new Book();
//        book.setBookName(bookName);
//        book.setIsbn(isbn);
//        //����������ѯͼ��
//        List<Book> bookList = bookService.select(book);
///*
//        ObservableList<Book> result = books;
//        if (bookFlag && isbnFlag) {
//            return;
//        }else {
//            if (!bookFlag){
//                result = books.filtered(s -> s.getBookName().contains(bookName));
//            }
//            if (!isbnFlag) {
//                result = books.filtered(s -> s.getIsbn().contains(isbn));
//            }
//        }
//*/
//        books = new ObservableListWrapper<Book>(new ArrayList<Book>(bookList));
//        bookTableView.setItems(books);
//    }
//
//    /*
//        �޸�
//     */
//    @FXML
//    private void bookEditView(MouseEvent event) {
//        try {
//            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
//            if (book == null){
//                Alerts.warning("δѡ��","����ѡ��Ҫ�޸ĵ��鼮");
//                return;
//            }
//
//           initStage(book);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//        ���
//     */
//    @FXML
//    private void bookAddView() {
//        try {
//            initStage(null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//    ��ʼ������stage
// */
//    private void initLendStage(Book book) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(App.class.getResource("/com/bjpowernode/module/book/BookLendView.fxml"));
//        StackPane target = (StackPane) loader.load();
//        Scene scene = new Scene(target);
//
//        Stage stage = new Stage();//������̨��
//        BookLendViewCtrl controller = (BookLendViewCtrl)loader.getController();
//        controller.setBookTableView(bookTableView);
//        controller.setStage(stage);
//        controller.setBook(book);
//        stage.setHeight(800);
//        stage.setWidth(700);
//        //���ô���ͼ��
//        stage.getIcons().add(new Image("icon.png"));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(scene); //������������̨��
//        stage.show(); //��ʾ���ڣ�
//    }
//
//    /*
//        ��ʼ��stage
//     */
//    private void initStage(Book book) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(App.class.getResource("/com/bjpowernode/module/book/BookHandleView.fxml"));
//        StackPane target = (StackPane) loader.load();
//        //Scene scene1 = App.getDecorator().getScene();
//        Scene scene = new Scene(target);
//
//
//        Stage stage = new Stage();//������̨��
//        BookHandleViewCtrl controller = (BookHandleViewCtrl)loader.getController();
//        controller.setStage(stage);
//        controller.setBooks(books);
//        controller.setBook(book);
//        controller.setBookTableView(bookTableView);
////        stage.setResizable(false);
//        stage.setHeight(800);
//        stage.setWidth(700);
//        //���ô���ͼ��
//        stage.getIcons().add(new Image("icon.png"));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(scene); //������������̨��
//        stage.show(); //��ʾ���ڣ�
//    }
}
