package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AccountGroupsController implements Initializable {
    @FXML
    private ContextMenu editMenu;
    @FXML
    private TreeView<String> treeView;

    TreeItem<String> Child = null;

    int cnt =0;

    @FXML
    TextField groupName;

    @FXML
    private Button save;
    @FXML
    Button exit;

    @FXML
    private MenuItem add;
    @FXML
    private MenuItem change;
    @FXML
    private MenuItem delete;

    private TreeItem<String> itemNode;

    private TreeItem<String> assets = new TreeItem<>("Assets");;
    private TreeItem<String> liabilities = new TreeItem<>("Liabilities");
    private TreeItem<String> income = new TreeItem<>("Income");
    private TreeItem<String> expenses = new TreeItem<>("Expenses");;



    private ObservableList<AccountCategory> categoriesList = FXCollections.observableArrayList();
    private ObservableList<Group> groupList = FXCollections.observableArrayList();
    //private ObservableList<Group> childrenList = FXCollections.observableArrayList();
    private GroupAdapter groupAdapter;
    private ArrayList TreeItems;
    TreeItem<String> child = null;
    private String selectedItemString;

    private AccountCategoryAdapter accountCategoryAdapter;
    private IFinanceController iFinanceController;
    private Boolean changeGroupName = false;
    private Boolean addNewGroup = false;

    public void setAdapters(AccountCategoryAdapter accountCategoryAdapter,GroupAdapter groupAdapter, IFinanceController iFinanceController) throws SQLException {
        this.accountCategoryAdapter = accountCategoryAdapter;
        this.groupAdapter = groupAdapter;
        this.iFinanceController = iFinanceController;
        populateTree();
    }

    public void populateArrayList(){
        groupList.clear();
        try {
            groupList.addAll(groupAdapter.getGroupsList());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Group List: " + ex.getMessage());
        }
        TreeItems = new ArrayList<>();
        for (Group group : groupList) {
            TreeItem<String> treeItem = new TreeItem<>(group.getName());
            TreeItems.add(group.getID(),treeItem);
        }
    }

    public void populateTree() throws SQLException {
        try {
            //don't use this method anymore, no need
            categoriesList.addAll(accountCategoryAdapter.getAccountCategoryList());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Account Category List: " + ex.getMessage());
        }

        try {
            groupList.addAll(groupAdapter.getGroupsList());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Group List: " + ex.getMessage());
        }

        TreeItem<String> dummyRoot = new TreeItem<>();

        treeView.setRoot(dummyRoot);
        treeView.setShowRoot(false);

        dummyRoot.getChildren().addAll(assets, liabilities, income, expenses);


        TreeItems = new ArrayList<>();

        for (Group group : groupList) {
            TreeItem<String> treeItem = new TreeItem<>(group.getName());
            TreeItems.add(group.getID(),treeItem);
        }

        TreeItem<String> child;
        TreeItem<String> Parent;

        for (Group group : groupList) {
            child = (TreeItem<String>) TreeItems.get(group.getID());
            Parent = (TreeItem<String>) TreeItems.get(group.getParent().getID());

            if (group.getParent().getID() == 0) {
                if (group.getElement().getName().equals("Assets")) {
                    Parent = assets;
                } else if (group.getElement().getName().equals("Liabilities")) {
                    Parent = liabilities;
                } else if (group.getElement().getName().equals("Income")) {
                    Parent = income;
                } else {
                    Parent = expenses;
                }
            }
            if(child.getValue()== "")
                continue;
            Parent.getChildren().add(child);
        }
        groupList.clear();
        categoriesList.clear();
            }

    public void rootOrBranch(){
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.isLeaf()) {
                branch();
            } else {
                root();
            }
    }

    public void root(){
    add.setDisable(false);
    change.setDisable(true);
    delete.setDisable(true);
    }
    public void branch(){
    add.setDisable(false);
    change.setDisable(false);
    delete.setDisable(false);
    }


    public void addNewGroup(){
    addNewGroup = true;
    groupName.setDisable(false);
    save.setDisable(false);

    }
    public void changeGroupName(){
        changeGroupName = true;
        groupName.setDisable(false);
        save.setDisable(false);
    }
    public void deleteGroup() throws SQLException {

       TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        selectedItemString = selectedItem.getValue();

        int id = groupAdapter.getID(selectedItemString);
        Group delete = groupAdapter.findRecord(id);
        groupAdapter.deleteRecord(delete);

        // Get the parent of the selected item
        TreeItem<String> parentItem = selectedItem.getParent();
        // Remove the selected item from the parent's list of children
        parentItem.getChildren().remove(selectedItem);
    }

    @FXML
    public void Save() throws SQLException {
        TreeItem<String> Parent;


    if(addNewGroup){

        //groupList.clear();
        //categoriesList.clear();
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        selectedItemString = selectedItem.getValue();

        int id = groupAdapter.getID(selectedItemString);

        TreeItem<String> Child = new TreeItem<>(groupName.getText());


        Parent = (TreeItem<String>) TreeItems.get(id);


        Group newGroup;
        if(id!=0) {
            Group parentGroup = groupAdapter.findRecord(id);

            newGroup = new Group(groupAdapter.getMax() + 1, groupName.getText(), parentGroup, parentGroup.getElement());

            TreeItems.add(newGroup.getID(),Child);

        }
        else {

            AccountCategory ac = accountCategoryAdapter.findRecord(selectedItemString);
            Group parentGroup = new Group();
            newGroup = new Group(groupAdapter.getMax() + 1, groupName.getText(), parentGroup, ac);
            TreeItems.add(newGroup.getID(),Child);

            if (newGroup.getElement().getName().equals("Assets")) {
                Parent = assets;
            } else if (newGroup.getElement().getName().equals("Liabilities")) {
                Parent = liabilities;
            } else if (newGroup.getElement().getName().equals("Income")) {
                Parent = income;
            } else {
                Parent = expenses;
            }
            addNewGroup=false;
        }
        groupAdapter.insertRecord(newGroup);
        Parent.getChildren().add(Child);
        groupName.setDisable(true);
        save.setDisable(true);
        groupName.setText("");
        addNewGroup = false;
    }
    if(changeGroupName){
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        selectedItemString = selectedItem.getValue();
        int id = groupAdapter.getID(selectedItemString);
        Group changeName = groupAdapter.findRecord(id);
        Group newGroup = new Group(changeName.getID(),groupName.getText(),changeName.getParent(),changeName.getElement());
        groupAdapter.updateRecord(newGroup);
        // Get the selected item in the TreeView
        //TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

    // Modify the value of the selected item
        selectedItem.setValue(groupName.getText());
        // Refresh the TreeView to reflect the changes
        treeView.refresh();
        groupName.setDisable(true);
        save.setDisable(true);
        groupName.setText("");
        changeGroupName = false;
    }
    }
    public void exitM() {
        // Get current stage reference
        Stage stage = (Stage) exit.getScene().getWindow();
        // Close stage
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Do any additional initialization here, if needed
        groupName.setDisable(true);
        save.setDisable(true);
    }

    // Add any additional methods here
}
