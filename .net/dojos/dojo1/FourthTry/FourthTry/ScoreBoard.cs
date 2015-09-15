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

        public void Play(int firstBall, int secondBall)
        {
            var frame = new Frame(firstBall, secondBall);
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }
    }
}