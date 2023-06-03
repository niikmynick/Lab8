package application.views

import application.AuthMode

class ViewsObjectPool {
    val authView = AuthView(AuthMode.LOGIN)
    val collectionView = CollectionView()
    val consoleView = ConsoleView()
    val loadingView = LoadingView()
    val mapView = MapView()
    val racoonView = RacoonView()
    val settingsView = SettingsView()
    val welcomeView = WelcomeView()
    val homeView = HomeView()
}