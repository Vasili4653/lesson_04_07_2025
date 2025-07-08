import java.util.Scanner;

public class ATM {
    private Menu menu;

    private long cardNumber;

    ATM(){
        menu = new Menu();
        menu.addOption(new MenuOption(1, "Выдача наличных", new MenuExecuter() {// создание анонимного объекта интерфейса
            @Override
            public void execute() {
                while (true) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Введите сумму");
                    long amount;
                    try {
                        amount = sc.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверная сумма");
                        continue;
                    }
                    try {
                        long newAmount = Bank.takeCash(cardNumber, amount);
                        System.out.print("Остаток на счету: ");
                        System.out.println(newAmount);
                        break;
                    } catch (CardBlockedException e) {
                        System.out.println("Ваша карта заблокирована");
                        break;
                    } catch (InvalidCardException e) {
                        System.out.println("Банкомат неисправен");
                        break;
                    } catch (LowMoneyException e) {
                        System.out.println("На счету недостаточно средств");
                        System.out.print("Доступно средств: ");
                        System.out.print(e.getAllowedAmount());
                        System.out.println(" BYN");
                    }
                }

            }
        }));
        menu.addOption(new MenuOption(2, "Остаток на счете", new MenuExecuter() {
            @Override
            public void execute() {
                try {
                    long amount = Bank.getCardAmount(cardNumber);
                    System.out.print("Текущий остаток: ");
                    System.out.println(amount);
                } catch (CardBlockedException e) {
                    System.out.println("Ваша карта заблокирована");
                } catch (InvalidCardException e) {
                    System.out.println("Банкомат неисправен");
                }
            }
        }));
        menu.addOption(new MenuOption(3, "Завершение работы", new MenuExecuter() {
            @Override
            public void execute() {
                menu.hide();
            }
        }));
    }
    public void showMainMenu(){
        menu.show();
    }

    public void requestCardNumber() throws CardBlockedException {
        while (true) {
            System.out.println("Введите номер карты");
            Scanner sc = new Scanner(System.in);
            try {
                cardNumber = sc.nextLong();
            } catch (Exception e){
                System.out.println("Неверный номер карты");
                continue;
            }
            try {
                Bank.startCardSession(cardNumber);
            } catch (InvalidCardException e) {
                System.out.println("Такая карта не найдена");
                continue;
            } catch (CardBlockedException e) {
                throw e;
            }
            break;
        }
    }

    public void requestPin() throws InvalidCardException, CardBlockedException {
        while (true) {
            System.out.println("Введите пин-код");
            Scanner sc = new Scanner(System.in);
            short pin;
            try {
                pin = sc.nextShort();
            } catch (Exception e) {
                System.out.println("Неверный ввод. Введите четыре цифры");
                continue;
            }
            try {
                Bank.checkPin(cardNumber, pin);
                break;
            } catch (InvalidCardException e) {
                System.out.println("Банкомат неисправен");
                throw e;
            } catch (IncorrectPinException e) {
                System.out.print("Неверный пин.");
                System.out.print("Осталось попыток: ");
                System.out.println(e.getAttemptsLeft());
            } catch (CardBlockedException e) {
                throw e;
            }
        }
    }
}
