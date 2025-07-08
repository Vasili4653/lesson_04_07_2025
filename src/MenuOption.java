public class MenuOption {
    private int number;
    private String name;
    private MenuExecuter  executer;

    public MenuOption(int number, String name, MenuExecuter executer){
        this.number = number;
        this.name = name;
        this.executer = executer;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void execute(){
        executer.execute();
    }
}
