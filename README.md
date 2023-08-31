# TD et TP IPI JVA324 - Architecture Distribuée (Spring Boot Cloud)

Application de type e-commerce avec un package de gestion de commandes client, qui utilise un package de gestion de stocks de produits (et de réception de réapprovisionnement fournisseur).

Le but des exercices (TD en séance et TP évalués) est de faire évoluer cette application monolithique vers une architecture en microservices : un de gestion de commandes client, un de gestion de stocks de produit, voire un de leur réception. NB. On suppose en effet que chacun de ces modules est géré par une équipe de développeurs différente pour répondre à des besoins utilisateurs différents exprimés dans une planification différente, plus grosse qu'un 1 ETP (Equivalent Temps Plein).


## Pré-requis

- Avoir installé un IDE :
    - IntelliJ Ultimate, avec votre adresse IPI sur Jetbrains Student à https://www.jetbrains.com/student/
    - sinon Eclipse, à https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-java-developers
- Savoir utiliser Git et les branches (utilisez les capacités Git de votre IDE ou installez-le séparément depuis
  https://git-scm.com/download/win ). Quelques liens :
    - https://learngitbranching.js.org/
    - https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging
- Avoir un compte Github. Voici comment y configurer l'authentification de git par clé SSH :
    - https://docs.github.com/en/authentication/connecting-to-github-with-ssh
    - https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account
- Connaître les bases du développement Java avec Maven (la persistence avec JPA est également utilisée ponctuellement),
  et au moins comment importer et compiler un projet Java dans l'IDE :
    - IntelliJ :
        - Installation de Git : Git > git not installed > Donwload and install
        - Cloner un projet Github : Git > Clone
        - Configuration d'un projet Maven : clic droit sur pom.xml > Add as Maven project ou bien voir IV-B-2 à https://damienrieu.developpez.com/tutoriel/java/nouveautes-intellij-12/?page=page_1
        - Installation de Java : par exemple
            - erreur ne trouve pas le symbol "java" : clic droit sur pom.xml > Build > sur Setup DSK choisir Configure > choisir Download et install (par exemple de la JDK Eclipse Temurin)
            - "Error running..." : Project JDK is not specified > Configure... > no SDK > Add SDK > Download
            - erreur "Cannot find JRE" : File > Project Structure (ou clic droit dans l'arborescence sur la racine d'un projet Maven), et de là dans Platform Settings > SDKs faire + > Download JDK (ou bien dans Project Settings > Project faire Add SDK > Download SDK)
        - lancer un build maven complet : Run > Edit configurations > Maven > Create configuration > mettre Working directory au dossier du projet et dans Command line, écrire : clean install
        - problème de sécurisation de connexion car proxy :
          - unable to access 'https://github.com/mdutoo/ipi-jva350-tptd.git/': SSL certificate problem: unable to get local issuer certificate
           Dans C:\Program Files\Git\etc\gitconfig, passer sous [http] la valeur sslBackend à schannel (et non openssl), comme dit à TODO
          - Maven error : unable to find valid certification path to requested target
          mvn clean package -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true
          ou dans IntelliJ Run > Edit Configurations > Java Options (sans -D) : maven.wagon.http.ssl.insecure=true maven.wagon.http.ssl.allowall=true
          comme dit à https://stackoverflow.com/questions/45612814/maven-error-pkix-path-building-failed-unable-to-find-valid-certification-path
          - unable to find valid certification path to requested target
          Aller sur les sites nodejs.org, (npmjs. ?) et registry.npmjs.com et télécharger leurs certificats (chaînes .pem) puis importer chacune dans le cacert de votre jdk (et non jre !) :
          "C:\Users\your_user\.jdks\temurin-20.0.1\bin\keytool" -import -v -trustcacerts -alias nodejs -file "C:\Users\your_user\dev\nodejs-org-chain.pem" -keystore "C:\Users\your_user\.jdks\temurin-20.0.1\lib\security\cacerts" -keypass changeit -storepass changeit
          "C:\Users\your_user\.jdks\temurin-20.0.1\bin\keytool" -import -v -trustcacerts -alias npmjs -file "C:\Users\your_user\dev\npmjs-com-chain.pem" -keystore "C:\Users\your_user\.jdks\temurin-20.0.1\lib\security\cacerts" -keypass changeit -storepass changeit
          "C:\Users\your_user\.jdks\temurin-20.0.1\bin\keytool" -import -v -trustcacerts -alias registry-npmjs -file "C:\Users\your_user\dev\registry-npmjs-com-chain.pem" -keystore "C:\Users\your_user\.jdks\temurin-20.0.1\lib\security\cacerts" -keypass changeit -storepass changeit
            "C:\Users\your_user\.jdks\temurin-20.0.1\bin\keytool" -import -v -trustcacerts -alias nodejs -file "C:\Users\your_user\dev\nodejs-org-chain.pem" -keystore "C:\Users\your_user\dev\ideaIC-2023.1.win\jbr\lib\security\cacerts" -keypass changeit -storepass changeit
            "C:\Users\your_user\.jdks\temurin-20.0.1\bin\keytool" -import -v -trustcacerts -alias npmjs -file "C:\Users\your_user\dev\npmjs-com-chain.pem" -keystore "C:\Users\your_user\dev\ideaIC-2023.1.win\jbr\lib\security\cacerts" -keypass changeit -storepass changeit
            "C:\Users\your_user\.jdks\temurin-20.0.1\bin\keytool" -import -v -trustcacerts -alias registry-npmjs -file "C:\Users\your_user\dev\registry-npmjs-com-chain.pem" -keystore "C:\Users\your_user\dev\ideaIC-2023.1.win\jbr\lib\security\cacerts" -keypass changeit -storepass changeit
          ET rajouter les propriétés maven -Djavax.net.ssl.trustStore="C:\Users\your_user\.jdks\temurin-20.0.1\lib\security\cacerts" -Djavax.net.ssl.trustStorePassword=changeit (ou dans IntelliJ Run > Edit Configurations > Java Options, mais là aussi sans -D)
          comme dit à https://stackoverflow.com/questions/54515921/cannot-install-node-through-front-end-maven-plugin-due-to-certificate-error
          - npm ERR! code UNABLE_TO_VERIFY_LEAF_SIGNATURE
          export NODE_EXTRA_CA_CERTS="C:\Users\marc.dutoo\dev\registry-npmjs-com-chain.pem"
          PUIS exécuter maven en ligne de commande au moins la première fois, par exemple avec git bash : MinGW64 (clic droit > git bash dans l'explorateur de fichiers ici)
          export JAVA_HOME="C:\Users\marc.dutoo\.jdks\temurin-20.0.1"
          "C:\Users\marc.dutoo\dev\ideaIC-2023.1.win\plugins\maven\lib\maven3\bin\mvn" clean install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Djavax.net.ssl.trustStore="C:\Users\your_user\.jdks\temurin-20.0.1\lib\security\cacerts" -Djavax.net.ssl.trustStorePassword=changeit -e
          comme dit à https://stackoverflow.com/questions/13913941/how-to-fix-ssl-certificate-error-when-running-npm-on-windows
          NB. par contre, configurer cafile dans ./npmrc ne marche pas car https obligé depuis October 4, 2021 comme le disent les logs

    - sinon Eclipse : voir https://thierry-leriche-dessirier.developpez.com/tutoriels/java/importer-projet-maven-dans-eclipse-5-min/
- Avoir installé postgresql (ou mysql) : https://www.postgresql.org/download/

## Créer la base de données

### PostgreSQL par image Docker

Pour lancer PostgreSQL dans une image Docker à l'aider du docker-compose.yml fourni :

    docker-compose up -d

NB. il est nécessaire de commenter les services autres que "db" si on souhaite démarrer les différents microservices directement en Java plutôt que dans Docker.

### PostgreSQL

Créer l'utilisateur "ipi" :

en tant qu'administrateur (sous Windows : recherche "cmd" dans les applications et dessus clic droit > "Run as admin", sous linux : sudo su - postgres) :

    $> psql
    $postgresql> create user ipi with password 'ipi' createdb;
    $postgresql> \q

Créer la base de données "ipi_jva324_distri" :

	$> psql -U ipi postgres -h localhost
	$> psql -U ipi postgres -h localhost
	$postgresql> create database ipi_jva324_distri encoding 'UTF8';
    $postgresql> \q

Vérifier que l'utilisateur créé peut bien se connecter à cette base :

	$> psql -U ipi ipi_jva324_distri -h localhost

Configurer l'application pour s'en servir : dans ```main/resources/application.properties```, décommenter les lignes sous "postgresql - clean setup".

## Exécution

### Directement en Java :

D'abord (et après tout changement d'interface utilisateur React.js) lancer une compilation complète : ```mvn clean install-DskipTests```

lancer la classe com.ipi.jva324.StockApplication
- dans l'IDE
    - IntelliJ : l'ouvrir et cliquer sur la flèche verte sur sa gauche
    - Eclipse : clic droit > Run as application),
- avec maven (IDE ou ligne de commande) : ```mvn spring-boot:run```

Puis pointer le navigateur web à http://localhost:8080/ .

Une fois d'autres microservices développés dans d'autres sous-modules maven, ils sont à lancer de la même manière.

### En Docker :

Pour faciliter le lancement de l'application initiale, et plus tard des autres microservices une fois ceux-ci développés, un docker-compose la comportant ainsi que sa base de données est fourni.
Pour s'en servir pour démarrer l'application :

    # build java :
    mvn -DskipTests clean install
    # build Docker des images et lancement, puis trace d'exécution :
    docker-compose up -d --build
    docker-compose logs -f --tail 500 stock-service
    # autres commandes utiles : status, ps -a, restart
    # arrêt ET suppression des conteneurs :
    docker-compose down


## Développement

Voici l'organisation du code source de l'application :
- code Java de l'application de départ : dans les package com.ipi.jva324.commande/stock(reception)
- web : Controllers web et API REST en Spring MVC dans les sous-packages web, SPA React.js dans src/main/resources/js (ou sinon possibilité de mettre des templates Thymeleaf dans main/resources/templates )
- couche de services métier : dans les sous-packages service
- persistence : en Spring Data JPA, avec modèles d'entités dans les sous-packages model (également utilisables en couche web), et repository dans les sous-packages éponymes
- initialisation des données : dans Commande/StockInitService
- tests : dans les dossers src/main/test
- configuration : src/main/resources/application.properties .
  - une base de données en mémoire (H2) est utilisée par défaut
  - Utiliser plutôt application-prod.properties (en activant le profile Spring "prod") permet de la faire fonctionner en Docker, même s'il est plus facile dans un premier temps de la faire fonctionner sans, y compris chaque micro-service ceux-ci une fois extraits.


## Exercices

### Application monolithique de départ

Forker ce repository Github dans votre propre compte Github. Après chaque question, vérifiez que les tests marchent toujours bien sûr ainsi que l'IHM, et committez et pushez vos changements.

[TD] Exécutez les tests unitaires. D'après eux, quelle vous semble être la partie du code le plus important de l'application et que fait-il ?

[TD] Exécutez l'application. Allez sur l'IHM web et essayez de l'utiliser. Reproduisez les 2 cas trouvés dans le code des tests unitaires. Notez les fonctionnalités qui semblent incomplètes (I, V, E) ou manquer (S) et leurs potentielles difficultés (U).

### Extraction du microservice "stock" - refactoring de l'appel en REST HAL

TODO NON [TD] (à ne faire que s'il n'existe pas encore) Copiez le module Maven d'origine vers 1 module "commande". Adaptez sa configuration de build (pom.xml) en conséquence, et branchez-la dans le pom. xml racine. Vérifiez que tests et IHM fonctionnent toujours pareil. Committez et pushez, et faites-le dans toutes les questions suivantes.

[TD] Quelle est la partie la plus importante de commandeService.createCommande() ? En écrire un test unitaire.

[TD] Rendez flexible l'appel de getProduit() par commandeService.createCommande(), en développant une interface ...commande.service.CommandeProduitService avec cette seule méthode et en l'implémentant dans un nouveau composant Spring (annoté @Component) de classe ...commande.service.CommandeProduitServiceLocalImpl qui utilise (injecté à l'aide d'@Autowired) ProduitService. Vérifiez que test et IHM marchent toujours.

[TD] Communication - exposition des produits en HAL : dans le module commande, activez Spring Data REST sur le chemin /api/data-rest  (voir cours).

[TD] Communication - consommation des produits : développez le composant Spring (annoté @Component) ...commande.service.CommandeProduitServiceRESTHALImpl implémentant l'interface CommandeProduitService à l'aide de RESTTemplate en de manière à utiliser plutôt l'API Spring Data HAL du microservice "stock" (plutôt que du code de persistence local comme jusqu'alors). Vérifiez que tests et IHM marchent toujours.

[TD] Quel problème apparaît en essayant de le faire marcher (démarrage test ou IHM), pourquoi ? Pour le résoudre, pour l'instant commentez l'annotation en tête de CommandeProduitLocalImpl. Vérifiez que tests et IHM marchent toujours.

[TD] Ecrivez dans une classe CommandeProduitServiceRestHalImplIntegrationTest un test d'intégration de CommandeProduitServiceRESTHALImpl, le plus simple possible (test de son appel distant).
    
[TD] Déplacez CommandeProduitServiceLocalImpl dans le dossier de sources "test" (plutôt que "main"). Faites en sorte que son test marche toujours. Pour cela, définissez son instanciation (aide: comme celle de RestTemplate, voir le cours...) dans une classe TestLocalConfiguration annotée @Configuration utilisée dans le test en annotant ce dernier @Import(TestLocalConfiguration). TODO cours

[TD] BONUS Ecrivez une version mockée du test existant de commandeService.createCommande().

[TD] Sortez la partie http://hôte:port de l'URL en propriétés de configuration, TODO utilisez-la à la place dans CommandeProduitRESTHALImpl.

[TP] faire pareil que dans CommandeService.createCommande() mais dans CommandeService.validateCommande(), afin de finir de ne plus utiliser ProduitService directement dans CommandeService.

### Extraction du microservice "stock" - nouveau microservice

[TD] Copiez le module Maven d'origine vers un module "stock". Adaptez sa configuration de build (pom.xml) en conséquence, et branchez-la dans le pom. xml racine.
- Développez dans ce module stock un test unitaire de la SEULE partie stock (dans com.ipi.jva324.stock) qui vérifie un bon usage nominal de manière simple (sans la partie ReceptionDeProduit), vérifiez qu'il marche.
  - Supprimez du module stock tout le code Java de la partie commande, et renommez et déplacez la classe de démarrage Spring Boot en com.ipi.jva324.stock.StockApplication. Vérifiez que le nouveau test unitaire marche toujours.
- Lancez le microservice Stock ainsi créé à l'aide de cette classe. Dans l'IHM qu'il sert, qu'est-ce qui marche encore et qu'est-ce qui ne marche plus, pourquoi ? Quelle est la solution classique, du point de vue web (C) ? du point de vue micro-services (G) ?

NB. En temps normal, chaque microservice serait dans son propre repository Github vu qu'il est géré par une équipe de développement différente (à moins d'une politique "monorepo" dans l'entreprise). Tous sont ici dans le même repository (et de builds raccrochés dans un pom.xml racine) uniquement pour simplifier la gestion des exercices.

[TD] Essayez de démarrer les 2 microservices en même temps, que constatez-vous ? Quelle est la cause, le principe de la solution, une solution qui entre autres utilise ce qui a été fait plus tôt ? Mettez-là en place. Validez que l'IHM remarche intégralement.

[TP] StockApi :
 - Sur le modèle de CommandeApi dans le module maven "commande"", développer dans le modèle maven "stock" une StockApi et son traitement d'un GET HTTP renvoyant un ProduitEnStock d'id donné.
- Ensuite, sur le modèle de CommandeProduitServiceRestHalImpl, développer CommandeProduitServiceRestImpl qui appelle cette nouvelle StockApi et non plus l'API automatiquement exposée au format REST HAL par Spring Data Rest.
- Faites-en un test d'intégration CommandeProduitServiceRestImplIntegrationTest sur le modèle de celui fait en TD dans CommandeProduitServiceRestHalImplIntegrationTest. Utilisez la même solution (@Import(TestRestConfiguration)) pour le faire marcher en parallèle des autres.

[TD] BONUS si vous êtes en avance, adaptez l'IHM de stock pour qu'elle s'en serve, voire commencez à développer une version Thymeleaf de l'IHM (par exemple une simple liste de produits).

TODO cours test API REST locale / fournie

[TD] Développez dans le module commande un composant Spring (annoté @Component) CommandeProduitServiceRESTImpl implémentant l'interface CommandeProduitService à l'aide de RESTTemplate appelant cette nouvelle API /api/produits de stock. Ecrivez un test d'intégration de CommandeService.createCommande() qui s'en sert TODO @...

### Discovery

[TD] générez la base d'un nouveau microservice Spring Boot à l'aide de start.spring.io (Spring Initializr), avec une configuration similaire à celle du module "commande", mais nommé "discovery" et de dépendances : cloud-eureka-server. Configurez ses propriétés comme pour un DiscoveryService. Démarrez-le en même temps que les précédents microservices et vérifiez que les logs de non-enregistrement de ces derniers disparaissent.

TODO s'assurer qu'il est utilisé dans RestTemplate : https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka
& passage à l'échelle en en instanciant d'autres instances, manuellement et aussi avec docker-compose scale

### Gateway

[TD] générez la base d'un nouveau microservice Spring Boot à l'aide de start.spring.io (Spring Initializr), avec une configuration similaire à celle du module "commande", mais nommé "gateway" et de dépendances : cloud-eureka,actuator,cloud-gateway,devtools . Configurez ses propriétés pour un déploiement sur un autre port (ex. 8081) et un autre nom pour le discovery service (ex. gateway), ainsi que avec des routes vers l'IHM et l'API /api/commandes du microservice commande et l'API /api/data-rest/produitEnStocks du microservice stock. Browsez sur ce port et vérifiez que l'IHM remarche intégralement. Pourquoi ?

### Actuator et monitoring

TODO
