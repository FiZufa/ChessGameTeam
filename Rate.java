public class Rate {
    static int pawnBoard[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}};
    static int rookBoard[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}};
    static int knightBoard[][]={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};
    static int bishopBoard[][]={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};
    static int queenBoard[][]={
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int kingMidBoard[][]={
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}};
    static int kingEndBoard[][]={
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}};

    public static int rate( int moving, int depth){
        int count = 0 ;
        int material = rateMaterial() ;
        count += rateAttack() ;
        count += material ;
        count += rateTheMove(moving, depth, material) ;
        count += rateCoor(material);
        ChessboardMain.flipBoard();
        material = rateMaterial() ;
        count -= rateAttack() ;
        count -= material ;
        count -= rateTheMove(moving, depth, material) ;
        count -= rateCoor(material);
        ChessboardMain.flipBoard();
        return -(count+depth*50) ;
    }
    public static int rateAttack(){
        int count = 0 ;
        int tempCoorBig = ChessboardMain.kingCoorBig ;
        for (int i=0 ; i < 64 ; i++){
            switch (ChessboardMain.chessBoard[i/8][i%8]) {
                case "P" : ChessboardMain.kingCoorBig = i  ;
                            if (!ChessboardMain.kingSafe()){
                                count -= 64 ;
                            }
                            break ;
                case "R" : ChessboardMain.kingCoorBig = i  ;
                            if (!ChessboardMain.kingSafe()){
                                count -= 500 ;
                             }
                            break ;
                case "K" : ChessboardMain.kingCoorBig = i  ;
                            if (!ChessboardMain.kingSafe()){
                                count -= 300 ;
                            }
                            break ;
                case "B" : ChessboardMain.kingCoorBig = i  ;
                            if (!ChessboardMain.kingSafe()){
                                count -= 300 ;
                            }
                            break ;
                case "Q" : ChessboardMain.kingCoorBig = i  ;
                            if (!ChessboardMain.kingSafe()){
                                count -= 900 ;
                            }
                            break ;
            }
        }
        ChessboardMain.kingCoorBig = tempCoorBig ;
        if (!ChessboardMain.kingSafe()){
            count -= 200 ;
        }
        return count/2;
    }
    public static int rateMaterial(){
        int count = 0 ;
        int countBishop = 0 ;
        for (int i=0 ; i < 64 ; i++){
            switch (ChessboardMain.chessBoard[i/8][i%8]) {
                case "P" : count += 100 ; break ;
                case "R" : count += 500 ; break ;
                case "K" : count += 300 ; break ;
                case "B" : countBishop += 1 ; break ;
                case "Q" : count += 900 ; break ;
            }
        }
        if (countBishop >= 2){
            count += 300*countBishop ;
        } else {
            if(countBishop == 1){
                count += 250 ;
            }
        }
        return count ;
    }

    public static int rateTheMove(int moveLength, int depth, int material){
        int count = 0 ;
        count += moveLength ;
        if (moveLength == 0){
            if ( !ChessboardMain.kingSafe()){
                count += -200000*depth ;
            } else {
                count += -150000*depth ;
            }
        }
        return count ;
    }

    public static int rateMove(){
        return 0 ;
    }
    public static int rateCoor(int material){
        int count = 0 ;
        for (int i=0 ; i < 64 ; i++){
            switch (ChessboardMain.chessBoard[i/8][i%8]) {
                case "P" : count += pawnBoard[i/8][i%8] ; break ;
                case "R" : count += rookBoard[i/8][i%8] ; break ;
                case "K" : count += knightBoard[i/8][i%8] ; break ;
                case "B" : count += bishopBoard[i/8][i%8] ; break ;
                case "Q" : count += queenBoard[i/8][i%8] ; break ;
                case "E" :
                    if (material >= 1750) {
                        count += kingMidBoard[i / 8][i / 8];
                        count += ChessboardMain.possibleE(ChessboardMain.kingCoorBig).length() * 10;
                    } else {
                        count += kingEndBoard[i / 8][i % 8];
                        count += ChessboardMain.possibleE(ChessboardMain.kingCoorBig).length() * 30;
                    }
                    break ;
                }
            }
        return count;
    }

    }
