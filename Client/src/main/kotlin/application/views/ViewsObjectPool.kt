package application.views

import application.AuthMode

class ViewsObjectPool {
    val authViewLogin = AuthView(AuthMode.LOGIN)
    val authViewReg = AuthView(AuthMode.REGISTRATION)
    val collectionView = CollectionView()
    val consoleView = ConsoleView()
    val loadingView = LoadingView()
    val mapView = MapView()
    val racoonView = RacoonView()
    val settingsView = SettingsView()
    val welcomeView = WelcomeView()
    val homeView = HomeView()
}