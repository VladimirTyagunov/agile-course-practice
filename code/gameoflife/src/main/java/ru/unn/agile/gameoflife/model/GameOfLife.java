package ru.unn.agile.GameOfLife.model;

public class GameOfLife {
    private char[][] grid;
    private int height;
    private int width;

    public GameOfLife(final int height, final int width) {
        if ((height < 0) && (width < 0)) {
            this.height = 0;
            this.width = 0;
        } else {
            this.height = height;
            this.width = width;
        }

        grid = new char[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public GameOfLife(final String str, final int height, final int width) {
        this(height, width);

        try {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    if (str.charAt(i * this.width + j) == '*') {
                        setCell(i, j);
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException  e) {
            System.out.println(e.getMessage());
        }
    }

    public GameOfLife(final GameOfLife game) {
        this(game.height, game.width);

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (game.isCellLive(i, j)) {
                    setCell(i, j);
                }
            }
        }

    }

    public boolean isPointOnGrid(final int y, final int x) {
        return ((y >= 0) && (y < this.height) && (x >= 0) && (x < this.width));
    }

    public void setCell(final int y, final int x) {
        if (isPointOnGrid(y, x)) {
            grid[y][x] = '*';
        }
    }

    public void deleteCell(final int y, final int x) {
        grid[y][x] = '.';
    }

    public boolean isCellLive(final int y, final int x) {
        if (isPointOnGrid(y, x)) {
            return grid[y][x] == '*';
        } else {
            return false;
        }
    }

    public int getNeighborsNum(final int y, final int x) {
        int neighborsNum = 0;
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if ((i != y) || (j != x)) {
                    neighborsNum += this.isCellLive(i, j) ? 1 : 0;
                }
            }
        }

        return neighborsNum;
    }

    public void makeTurn() {
        GameOfLife tempGame = new GameOfLife(this);

        final int maxNeighbours = 3;
        final int neighboursToReproduce = 3;
        final int minNeighbours = 2;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if ((tempGame.getNeighborsNum(i, j) < minNeighbours)
                 || (tempGame.getNeighborsNum(i, j) > maxNeighbours)) {
                    deleteCell(i, j);
                }
                if (tempGame.getNeighborsNum(i, j) == neighboursToReproduce) {
                    setCell(i, j);
                }
            }
        }
    }

}
