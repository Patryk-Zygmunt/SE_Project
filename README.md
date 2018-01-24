# On!tor

## 

## JAK DODAĆ BAZĘ: 
    sudo su postgres
    psql
    CREATE USER agh WITH PASSWORD 'AGH-salaries';
    CREATE DATABASE servers_info owner agh;
    
## JAK URUCHOMIĆ SERWER
    java -jar server.jar
    
## JAK URUCHOMIĆ FRONTEND
    przejść do folderu `dist`
    uruchomić plik `index.html`

## JAK URUCHOMIĆ AGENTA
    przejdź do folderu `agent`
    python3 agent.py

    
    
    
