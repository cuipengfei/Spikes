using System.Collections.Generic;
using System.Linq;

namespace Bowling
{
    public class ScoreBoard
    {
        private readonly List<Frame> frames = new List<Frame>();

        public int Score => TotalScore();

        public void AddFrame(int firstTry, int secondTry)
        {
            var frame=new Frame {FirstTry = firstTry,SecondTry = secondTry};
            AddFrame(frame);
        }

        private int TotalScore()
        {
            return frames.Sum(frame => frame.Score);
        }

        public void AddLastFrame(int firstTry, int secondTry, int thirdTry)
        {
            var frame=new LastFrame {FirstTry = firstTry,SecondTry =secondTry,ThirdTry = thirdTry};
            AddFrame(frame);
        }

        private void AddFrame(Frame frame)
        {
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }
    }
}