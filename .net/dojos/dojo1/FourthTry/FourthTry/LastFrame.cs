namespace FourthTry
{
    public class LastFrame:Frame
    {
        private int _thirdBall;

        public LastFrame(int firstBall, int secondBall, int thirdBall) : base(firstBall,secondBall)
        {
            this._thirdBall = thirdBall;
        }

        protected override int CalculateScore()
        {
            return FirstBall + SecondBall + _thirdBall;
        }

        protected override void CheckBallLimit()
        {
           CheckSingleBall(FirstBall,nameof(FirstBall));
           CheckSingleBall(FirstBall,nameof(SecondBall));
           CheckSingleBall(_thirdBall,nameof(_thirdBall));
        }
    }
}