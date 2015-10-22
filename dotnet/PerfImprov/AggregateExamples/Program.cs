using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using AggregateExamples.FunctionalPath;
using AggregateExamples.NormalPath;

namespace AggregateExamples
{
    internal class Program
    {
        private static readonly IEnumerable<Company> _smallCompanies = GenerateSmallCompanies();

        private static void Main(string[] args)
        {
            for (var i = 0; i < 10; i++)
            {
                TryAll();
            }
            Console.ReadLine();
        }

        private static void TryAll()
        {
            Console.WriteLine("==========================");

            PrintMergeResult(LinearMerger.LinearMerge, "简单直接    （最容易）");
            PrintMergeResult(FirstTryParallelMerger.FirstTryParallelMerge, "错误并行    （快但错）");
            PrintMergeResult(LockMerger.MergeWithLock, "加锁并行    （反而慢）");

            Console.WriteLine("*****");

            PrintMergeResult(FunctionalMerger.FunctionalMerge, "函数式合并    （持平）");
            PrintMergeResult(FunctionalParallelMerger.FunctionalParallelMerge, "函数式并行合并（最快）");
        }

        private static void PrintMergeResult(Func<Company, IEnumerable<Company>, Company> mergeMethod,
            string funcApproach)
        {
            var stopwatch = new Stopwatch();
            stopwatch.Start();

            var mergedCompany = mergeMethod(new Company {EvaluatedMarketValue = 10000000}, _smallCompanies);

            stopwatch.Stop();

            Console.WriteLine(funcApproach + ": " + mergedCompany.EvaluatedMarketValue + " Time: " +
                              stopwatch.ElapsedMilliseconds);
        }

        private static IEnumerable<Company> GenerateSmallCompanies()
        {
            return Enumerable
                .Range(100, 12000000)
                .Select(number => new Company {EvaluatedMarketValue = number/100});
        }
    }
}