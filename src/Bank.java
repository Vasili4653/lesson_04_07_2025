public class Bank {
    private static Card[] cards = {
            new Card(123l, (short)1234, 1200l),
            new Card(628l, (short)3274, 1000l),
            new Card(824l, (short)9224, 500l),
    };

    public static void startCardSession(long cardNumber) throws InvalidCardException, CardBlockedException {
        Card card = findCard(cardNumber);
    }

    public static void checkPin(long cardNumber, short pin) throws InvalidCardException, IncorrectPinException, CardBlockedException {
        Card card = findCard(cardNumber);
        if(card.getPin() == pin){
            return;
        }
        card.setFailPinCounter(card.getFailPinCounter() + 1);
        if (card.getFailPinCounter() < 3){
            throw new IncorrectPinException(3 - card.getFailPinCounter());
        }
        throw new CardBlockedException();
    }

    public static long getCardAmount(long cardNumber) throws CardBlockedException, InvalidCardException {
        Card card = findCard(cardNumber);
        return card.getAmount();
    }

    public static long takeCash(long cardNumber, long cashAmount) throws CardBlockedException, InvalidCardException, LowMoneyException {
        Card card = findCard(cardNumber);
        return card.changeAmount(cashAmount);
    }

    private static Card findCard(long cardNumber) throws CardBlockedException, InvalidCardException {
        for(Card card : cards){
            if(card.getNumber() == cardNumber){
                if(card.isBlocked()){
                    throw new CardBlockedException();
                }
                return card;
            }
        }
        throw new InvalidCardException();
    }
}
