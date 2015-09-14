using System.Collections.Generic;
using System.Linq;

namespace ThirdTry
{
    public class ScoreBoard
    {
       private List<Frame> frames=new List<Frame>(); 

        public int TotalScore { get { return frames.Sum(f => f.Score); } }

        public void Play(int firstBall, int secondBall,int thirdBall=-1)
        {
            var frame = FrameFactory(firstBall, secondBall, thirdBall);
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }

        private static Frame FrameFactory(int firstBall, int secondBall, int thirdBall)
        {
            var frame = new Frame(firstBall, secondBall);
            if (thirdBall != -1)
            {
                frame = new LastFrame(firstBall, secondBall, thirdBall);
            }
            return frame;
        }
    }
}