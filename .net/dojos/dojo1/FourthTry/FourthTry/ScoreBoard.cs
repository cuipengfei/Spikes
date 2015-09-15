using System.Collections.Generic;
using System.Linq;

namespace FourthTry
{
    public class ScoreBoard
    {
        private readonly List<Frame> frames=new List<Frame>(); 
        public ScoreBoard()
        {
        }

        public int TotalScore { get { return frames.Sum(frame => frame.Score); } }

        public void Play(int firstBall, int secondBall,int thirdBall=-1)
        {
            var frame = CreateFrame(firstBall, secondBall, thirdBall);
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }

        private static Frame CreateFrame(int firstBall, int secondBall, int thirdBall)
        {
            Frame frame;
            if (thirdBall == -1)
            {
                frame = new Frame(firstBall, secondBall);
            }
            else
            {
                frame = new LastFrame(firstBall, secondBall, thirdBall);
            }
            return frame;
        }
    }
}