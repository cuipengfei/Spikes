from deepeval import assert_test
from deepeval.metrics import GEval
from deepeval.test_case import LLMTestCase, LLMTestCaseParams
from langchain_openai import ChatOpenAI

from MyCustomModel import MyCustomModel
from QALogger import QALogger


def test_case():
    qwen = ChatOpenAI(
        model="qwen-max",
        api_key="xxx",
        base_url="https://dashscope.aliyuncs.com/compatible-mode/v1",
        callbacks=[QALogger()]
    )

    correctness_metric = GEval(
        name="Correctness",
        model=MyCustomModel(model=qwen),
        criteria="Determine if the 'actual output' is correct based on the 'expected output'.",
        evaluation_params=[LLMTestCaseParams.ACTUAL_OUTPUT, LLMTestCaseParams.EXPECTED_OUTPUT],
        threshold=0.99,
        verbose_mode=True
    )
    test_case = LLMTestCase(
        input="What if these shoes don't fit?",
        # Replace this with the actual output from your LLM application
        actual_output="You have 30 days to get a full refund at no extra cost.",
        expected_output="We offer a 30-day full refund at no extra costs.",
        retrieval_context=["All customers are eligible for a 30 day full refund at no extra costs."]
    )
    assert_test(test_case, [correctness_metric])
