package ar.edu.unlu.poo.escobaDe15.Observer;

public interface Subject {
    public void attach(Observer anObserver);
    public void detach(Observer anObserver);
    public void notifyMessage(Notificador notificador);
}
