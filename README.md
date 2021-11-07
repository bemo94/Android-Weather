Professionnaliser vos développements mobiles Android
===

1. Récupérer le web service forecast de l'API OpenWeatherMap (en dégré Celsus) avec Retrofit.
    * Utiliser MockWebServer pour renvoyer le json contenu dans les resources de tests lors d'un appel à Retrofit
    * Utiliser les méthodes synchrone de Retrofit 
    * Tester la bonne récupération du JSON
    
2. Parser le forecast avec Jackson et Autovalue en complément de Retrofit
    * Paramétrer Retrofit pour fonctionner avec la librairie Jackson
    * Utiliser AutoValue pour créer les POJO nécessaires à parser le flux json
    * Insérer les bonnes annotations Jackson pour faire le lien entre le flux JSON et les POJO que doit générer AutoValue
    * Créer un Transformer afin de tranformer les objets provenants du JSON en CityWeeklyForecast
    * Customiser Jackson pour gérer convenablement le format Date
    
3. Insérer et récupérer un CityWeeklyForecast avec Realm
    * Créer les objets Realm nécessaires
    * Transformer les CityWeeklyForecast en objets Realm pour pouvoir les insérer en base
    * Faire en sorte qu'à chaque mise à jour d'un CityWeeklyForecast, les Forecast précédents soient supprimés.
    
4. Créer le use-case qui permet de récupérer et de traiter un CityWeeklyForecast
    * S'assurer de la validité des inputs
    * Informer quand un traitement est en cours
    * Récupérer les données du repository
    * Donner à l'OutputPort le Forecast avec la température la plus élevée et celui avec la température la plus faible.
     Seuls les Forecast entre 8:00 et 20:00 sont pertinents.
     
5. Créer la couche de présentation pour rendre affichable un CityWeeklyForecast
    * Proposer un message d'erreur cohérent pour chaque exception possible
    * Dans le cas d'un succès :
        * Rappeler la ville
        * Indiquer la date de la meilleure et de la pire prévision (juste le jour suffit)
        * Indiquer le degré de la meilleure et de la pire prévision
        * Distinguer le cas "Aujourd'hui" des autre cas 
        
6. Intégrer la couche présentation et use-cases dans l'app
    * Importer les projets use-cases et presentation (pas besoin du repository pour l'instant)
    * Dans le module app :
        * Créer une classe CityForecastController avec pour seule méthode loadCityForecast(String city),
        dont la responsabilité est 
            1. De prendre un nom de ville en paramètre, 
            2. De le "trimmer" si nécessaire (test à l'appui)
            3. De la passer à méthode de l'Interactor 
        * Créer une classe MockCityForecastRepository qui implémente CityForecastRepository et 
        qui renvoie toujours un même CityWeeklyForecast
        * Faire implémenter l'interface CityForecastView par la CityForecastActivity, 
        et mettre un log dans les méthodes implementées
        * Créer une classe CityForecastModule qui va injecter toute les dépendances nécessaires :
            1. En constructeur de cette classe, se trouve l'Activity, qui joue le rôle de View
            2. Instancier un Presenter, en lui fournissant la View (l'Activity)
            3. Instancier un Interactor, en lui fournissant le Presenter, et le MockCityForecastRepository
            4. Instancier un Controller, en lui fournissant un Interactor
            5. Créer une méthode pour accéder au Controller
        * Dans le onCreate de l'Activity :
            1. Instancier le CityForecastModule, 
            2. Récuperer le controller, et appeler sa méthode avec un paramètre en dur
    * Faire évoluer le MockCityForecastRepository pour renvoyer des exceptions en fonction des 
    paramètres reçus
    
7. Utiliser le pattern Decorator autour du Controller et de la View pour gérer les appels en asynchrones :
    1. Créer un ControllerAsync qui execute tout les appels au Controller sur un Worker thread 
    2. Créer une ViewAsync qui execute tout les appels à la View sur un Main thread
    3. Fournir le ControllerAsync à l'Activity, et la ViewAsync au Presenter
    
8. Utiliser la librairie ExecutorDecorator pour générer les classes précédemment créées

9. Remplacer le FakeCityForecastRepository par le WeatherNetworkRepository
    1. Faire implémenter l'interface CityForecastRepository par le WeatherNetworkRepository,
    2. Après avoir créer les tests correspondant, faire en sorte que le WeatherNetworkRepository 
    renvoie les exceptions GenericException et UnknownCityException dans les cas appropriés.
    3. Mettre à jour le CityForecastModule pour qu'il utilise le WeatherNetworkRepository
    
10. Mettre à jour l'application avec le design final
    1. Ajouter un listener de l'action Search du Keyboard pour déclencher la recherche
    2. Sélectionner le bon enfant du ViewFlipper en fonction des différentes méthodes appelées dans 
    la CityForecastActivity
    3. Pour la méthode setMessage, renseigner le message au binding, et l'utiliser dans le 
    TextView approprié
    4. Pour la méthode setViewModel, renseigner le ViewModel au binding, et l'utiliser dans les 
    TextView appropriés