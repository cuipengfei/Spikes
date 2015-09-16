using System;
using System.Collections.Generic;
using System.Linq;

namespace LiveDemo
{
    public class ScoreBoard
    {
        private List<Frame> frames=new List<Frame>(); 

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
            var frame = new Frame(firstBall, secondBall);
            if (thirdBall != -1)
            {
                frame = new LastFrame(firstBall, secondBall, thirdBall);
            }
            return frame;
        }

        public int TotalScore
        {
            get { return frames.Sum(frame => frame.Score); }
        }
    }
}