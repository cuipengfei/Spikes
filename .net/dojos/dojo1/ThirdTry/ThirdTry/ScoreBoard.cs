using System.Collections.Generic;
using System.Linq;

namespace ThirdTry
{
    public class ScoreBoard
    {
       private List<Frame> frames=new List<Frame>(); 

        public int TotalScore { get { return frames.Sum(f => f.Score); } }

        public void Play(int firstBall, int secondBall)
        {
            var frame=new Frame(firstBall, secondBall);
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }
    }
}