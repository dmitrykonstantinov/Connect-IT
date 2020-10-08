package breakingumbrella.connectit.domain.campaign;

import java.util.Random;

import javax.inject.Inject;

import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;
import breakingumbrella.connectit.entity.profile.CampaignPosition;

public class GameFieldCreator {

    private Random random = new Random();

    public final static int totalTripCount = 16; //Should be the same in CampaignLvls

    @Inject
    GameFieldCreator() {
    }

    public GameContext createGameField(GameContext gameContext, CampaignPosition curPos) {

        int tripSector = getSectorBaseOnTrip(curPos.getTrip());
        GameField gameField = new GameField(tripSector + 4, tripSector + 4);
        int turnsAmount = gameField.getSizeX() * gameField.getSizeY();
        gameContext.setGameField(gameField);


        switch (tripSector) {
            case 1:
                turnsAmount = createForFirstTripSector(gameContext, gameField, turnsAmount);
                break;
            case 2:
                turnsAmount = createForSecondTripSector(gameContext, gameField, turnsAmount);
                break;
            case 3:
                turnsAmount = createForThirdTripSector(gameContext, gameField, turnsAmount);
                break;
            case 4:
                turnsAmount = createForFourthTripSector(gameContext, gameField, turnsAmount);
                break;
        }

        gameContext.getGameSettings().setTargetTurnsNumber(turnsAmount);

        return gameContext;
    }

    private int createForFirstTripSector(GameContext gameContext, GameField gameField, int turnsAmount) {
        int enemyTuPut = gameField.getSizeX(); //At least 5
        int playerTuPut = gameField.getSizeX();
        //Already putted
        for (int i = 0; i < enemyTuPut; i++) {
            Figure figure = createFigure(FigureTypes.circle, gameContext);
            gameField.addFigure(figure);
        }

        for (int i = 0; i < playerTuPut; i++) {
            gameField.addFigure(createFigure(FigureTypes.cross, gameContext));
        }
        turnsAmount -= enemyTuPut + playerTuPut;
        return turnsAmount;
    }

    private int createForSecondTripSector(GameContext gameContext, GameField gameField, int turnsAmount) {
        int enemyTuPut = gameField.getSizeX(); //At least 5
        int playerTuPut = gameField.getSizeX();
        //Already putted
        for (int i = 0; i < enemyTuPut; i++) {
            Figure figure = createFigure(FigureTypes.circle, gameContext);
            gameField.addFigure(figure);
        }

        for (int i = 0; i < playerTuPut; i++) {
            gameField.addFigure(createFigure(FigureTypes.cross, gameContext));
        }
        turnsAmount -= enemyTuPut + playerTuPut;
        return turnsAmount;
    }


    private int createForThirdTripSector(GameContext gameContext, GameField gameField, int turnsAmount) {

        //Uncrossable - 5 units
        for (int i = 0; i < 3; i++) {
            Figure figure = createFigure(FigureTypes.circle, gameContext);
            figure.setAbilityType(AbilityType.durableFigure);
            gameField.addFigure(figure);
        }
        for (int i = 0; i < 2; i++) {
            Figure figure = createFigure(FigureTypes.cross, gameContext);
            figure.setAbilityType(AbilityType.durableFigure);
            gameField.addFigure(figure);
        }
        turnsAmount -= 5;

        int enemyTuPut = gameField.getSizeX(); //At least 5
        int playerTuPut = gameField.getSizeX();
        //Already putted
        for (int i = 0; i < enemyTuPut; i++) {
            Figure figure = createFigure(FigureTypes.circle, gameContext);
            gameField.addFigure(figure);
        }

        for (int i = 0; i < playerTuPut; i++) {
            gameField.addFigure(createFigure(FigureTypes.cross, gameContext));
        }
        turnsAmount -= enemyTuPut + playerTuPut;
        return turnsAmount;
    }

    private int createForFourthTripSector(GameContext gameContext, GameField gameField, int turnsAmount) {

        //Uncrossable - 5 units
        for (int i = 0; i < 5; i++) {
            Figure figure = createFigure(FigureTypes.crossed_circle, gameContext);
            figure.setAbilityType(AbilityType.durableFigure);
            gameField.addFigure(figure);
        }
        for (int i = 0; i < 3; i++) {
            Figure figure = createFigure(FigureTypes.crossed_cross, gameContext);
            gameField.addFigure(figure);
        }
        turnsAmount -= 8;

        int enemyTuPut = gameField.getSizeX(); //At least 5
        int playerTuPut = gameField.getSizeX();
        //Already putted
        for (int i = 0; i < enemyTuPut; i++) {
            Figure figure = createFigure(FigureTypes.circle, gameContext);
            gameField.addFigure(figure);
        }

        for (int i = 0; i < playerTuPut; i++) {
            gameField.addFigure(createFigure(FigureTypes.cross, gameContext));
        }
        turnsAmount -= enemyTuPut + playerTuPut;
        return turnsAmount;
    }

    private int getSectorBaseOnTrip(int tripLvl) {
        if(tripLvl < 4) {
            return 1;
        }
        else if(tripLvl < 8) {
            return 2;
        }
        else if(tripLvl < 12) {
            return 3;
        }
        else return 4;
    }

    private Figure createFigure(Integer figureType, GameContext gameContext) {
        Figure figure = new Figure(figureType);
        int positionX = 0;
        int positionY = 0;
        boolean firstRun = true; //It's need to avoid always 0
        while (gameContext.getGameField().isFigureExistAtPosition(positionX, positionY) || firstRun) {
            firstRun = false;
            positionX = random.nextInt(gameContext.getGameField().getSizeX());
            positionY = random.nextInt(gameContext.getGameField().getSizeY());
        }
        figure.setPosition(positionX, positionY);
        return figure;
    }
}
