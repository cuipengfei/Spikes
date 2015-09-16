using System;
using System.Collections.Generic;
using System.Linq;

namespace LiveDemo
{
    public class ScoreBoard
    {
        private List<Frame> frames=new List<Frame>(); 

        public void Play(int firstBall, int secondBall)
        {
            var frame=new Frame(firstBall,secondBall);
            if (frames.Count > 0)
            {
                frames.Last().Next = frame;
            }
            frames.Add(frame);
        }

        public int TotalScore
        {
            get { return frames.Sum(frame => frame.Score); }
        }
    }
}