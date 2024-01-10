<style>
    #header{
        color: #ed9e70;
    }
    #main{
        display: flex;
    }
    #image{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #pgn{
        width:100%;
        min-height: 200px;
        max-width: 400px;
    }
</style>

<body>
<img src="src/main/resources/img/logo.png" alt="Penguin Run" width=100% height=100%>

<div id="main">
    <div id="text">
        <h2 id="header"> How to start game </h2>
        <p>
            In order to run the game, the Main.java class has to be opened and executed.
        </p>
        <h2 id="header">Controls</h2>
        <p>
            <li>WASD or arrow keys : move penguin towards desired direction </li>
            <li>Press ESC key to pause the game</li>
        </p>
        <h2 id="header">How to play</h2>
        <p>The goal is to get to the end of the maze before the time runs out. 
        The exit is located at the bottom right of the maze.</p>
    </div>
    <div id="image"><img src="src/main/resources/img/pgnBig.png" alt="Penguin Run" id="pgn"></div>
</div>
</body>


