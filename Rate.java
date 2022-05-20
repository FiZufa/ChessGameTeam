public class Rate {

    public static int rate( int moving, int depth){
        int count = 0 ;
        count += rateAttack() ;
        count += rateMaterial() ;
        count += rateMove() ;
        count += rateCoor() ;
        ChessboardMain.flipBoard();
        count -= rateAttack() ;
        count -= rateMaterial() ;
        count -= rateMove() ;
        count -= rateCoor() ;
        ChessboardMain.flipBoard();
        return -(count+depth*50) ;
    }
    public static int rateAttack(){
        return 0 ;
    }
    public static int rateMaterial(){
        int count = 0 ;
        int countBishop = 0 ;
        for (int i=0 ; i < 64 ; i++){
            switch (ChessboardMain.chessBoard[1/8][1%8]) {
                case "P" : count += 100 ; break ;
                case "R" : count += 500 ; break ;
                case "K" : count += 300 ; break ;
                case "B" : countBishop += 1 ; break ;
                case "Q" : count += 900 ; break ;
            }
        }
        if (countBishop >= 2){
            return count += 300*countBishop ;
        } else {
            if(countBishop == 1){
                count += 250 ;
            }
        }
        return count ;
    }
    public static int rateMove(){
        return 0 ;
    }
    public static int rateCoor(){
        return 0 ;
    }


}
