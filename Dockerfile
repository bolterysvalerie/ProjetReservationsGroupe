# Utilise une image de base JDK 21
FROM openjdk:21-jdk


# Définit le répertoire de travail dans le conteneur
WORKDIR /app

# Copie le fichier JAR dans le conteneur
COPY target/*.jar app.jar

# Expose le port sur lequel l'application écoute
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
