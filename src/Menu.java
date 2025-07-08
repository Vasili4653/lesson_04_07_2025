import java.util.Scanner;

public class Menu {
    private MenuOption[] options = new MenuOption[10];

    private int optionsCount;

    private boolean isStopped;

    public void addOption(MenuOption option){
        options[optionsCount++] = option;
    }

    public void hide(){
        isStopped = true;// закрываем цикл метода show
    }

    public void show(){
        while(!isStopped){
            // 1. Отобразить пункты меню(menu options)
            for(int i = 0; i < optionsCount; i++){
                MenuOption opt = options[i];
                System.out.print(opt.getNumber());
                System.out.println(". " + opt.getName());
            }
            // 2. Попросить пользователя ввести номер пункта меню.
            Scanner sc = new Scanner(System.in);
            int n;
            try{
                n = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Неверный ввод. Повторите попытку");
                continue;
            }
            // 3. Найти пункт меню по введеному номеру.
            MenuOption selectedOption = null;
            for(int i = 0; i < optionsCount; i++){
                MenuOption opt = options[i];
                if(n == opt.getNumber()){
                    selectedOption = opt;
                    break;
                }
            }
            if(selectedOption == null){
                System.out.println("Выбран неверный пункт меню");
                continue;
            }
            // 4. Нужно выполнить выбранный пункт меню.
            selectedOption.execute();
        }
    }
}
