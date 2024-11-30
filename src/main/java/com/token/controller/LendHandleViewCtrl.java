package com.token.controller;



import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class LendHandleViewCtrl {

    @FXML
    private TextField lendIdField;

    @FXML
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publisherField;

    @FXML
    private ComboBox typeField;

//    private Stage stage;
//
//    private TableView<Lend> lendTableView;
//
//    private ObservableList<Lend> lends;
//
//    //�޸ĵ�lend����
//    private Lend lend;
//
//    /*
//        ��ӻ��޸�����
//     */
//    @FXML
//    private void addOrEditLend() {
//        try {
//            String id = lendIdField.getText();
//            if ("".equals(id) || null == id) {
//                //��Ӳ���
//                Lend lend = new Lend();
//                populate(lend);
//                lend.setStatus(Constant.STATUS_STORAGE);
//                lends.add(lend);
//            }else {
//                //�޸Ĳ���
//                populate(this.lend);
//                //ˢ��
//                lendTableView.refresh();
//            }
//
//            stage.close();
//            Alerts.success("�ɹ�", "�����ɹ�");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Alerts.error("ʧ��","����ʧ��");
//        }
//
//    }
//
//    private void populate(Lend lend) {
////        lend.setLendName(lendNameField.getText());
////        lend.setAuthor(authorField.getText());
////        lend.setIsbn(isbnField.getText());
////        lend.setPublisher(publisherField.getText());
////        lend.setType(typeField.getSelectionModel().getSelectedItem().toString());
//    }
//
//    @FXML
//    private void closeView() {
//        stage.close();
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
//    public ObservableList<Lend> getLends() {
//        return lends;
//    }
//
//    public void setLends(ObservableList<Lend> lends) {
//        this.lends = lends;
//    }
//
//    public Lend getLend() {
//        return lend;
//    }
//
//    public void setLend(Lend lend) {
//        this.lend = lend;
//        if (lend != null) {
//            //����޸Ľ����ֵ
////            lendNameField.setText(lend.getLendName());
////            authorField.setText(lend.getAuthor());
////            isbnField.setText(lend.getIsbn());
////            lendIdField.setText(String.valueOf(lend.getId()));
////            publisherField.setText(lend.getPublisher());
////            typeField.setValue(lend.getType());
//        }
//
//    }
//
//    public TableView<Lend> getLendTableView() {
//        return lendTableView;
//    }
//
//    public void setLendTableView(TableView<Lend> lendTableView) {
//        this.lendTableView = lendTableView;
//    }
}
