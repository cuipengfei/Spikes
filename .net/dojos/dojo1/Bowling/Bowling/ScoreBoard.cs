using System.Collections.Generic;
using System.Linq;

namespace Bowling
{
    public class ScoreBoard
    {
        private readonly List<Frame> frames = new List<Frame>();

        public int Score => TotalScore();

        public void AddFrame(Frame frame)
        {
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }

        private int TotalScore()
        {
            return frames.Sum(frame => frame.Score);
        }
    }
}