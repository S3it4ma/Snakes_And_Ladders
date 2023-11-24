package appState.sidebarState;

public interface State {
    void show();
    State nextState();
}
