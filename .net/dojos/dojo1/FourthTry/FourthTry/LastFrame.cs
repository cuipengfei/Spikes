namespace FourthTry
{
    public class LastFrame : Frame
    {
        private readonly int _thirdBall;

        public LastFrame(int firstBall, int secondBall, int thirdBall) : base(firstBall, secondBall)
        {
            _thirdBall = thirdBall;
        }

        protected override int CalculateScore()
        {
            return FirstBall + SecondBall + _thirdBall;
        }

        protected override void CheckExtra()
        {
            CheckSingleBall(_thirdBall, nameof(_thirdBall));
        }
    }
}