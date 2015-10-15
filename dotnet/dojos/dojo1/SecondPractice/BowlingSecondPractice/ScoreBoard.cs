using System.Collections.Generic;
using System.Linq;

namespace BowlingSecondPractice
{
    public class ScoreBoard
    {
        private readonly List<Frame> frames = new List<Frame>();

        public int TotalScore
        {
            get { return frames.Sum(frame => frame.Score); }
        }

        public void PlayFrame(int firstBall, int secondBall, int thirdBall = -1)
        {
            var frame = CreateFrame(firstBall, secondBall, thirdBall);
            if (frames.Count > 0)
            {
                frames.Last().NextFrame = frame;
            }
            frames.Add(frame);
        }

        private static Frame CreateFrame(int firstBall, int secondBall, int thirdBall)
        {
            Frame frame = null;
            if (thirdBall != -1)
            {
                frame = new LastFrame(firstBall, secondBall, thirdBall);
            }
            else
            {
                frame = new Frame(firstBall, secondBall);
            }
            return frame;
        }
    }
}