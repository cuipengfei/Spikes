from deepeval.models.base_model import DeepEvalBaseLLM


class MyCustomModel(DeepEvalBaseLLM):
    def __init__(
            self,
            model
    ):
        self.model = model

    def load_model(self):
        loaded_model = self.model
        return loaded_model

    def generate(self, prompt: str) -> str:
        chat_model = self.load_model()
        response = chat_model.invoke(prompt).content
        return response

    async def a_generate(self, prompt: str) -> str:
        chat_model = self.load_model()
        res = await chat_model.ainvoke(prompt)
        return res.content

    def get_model_name(self):
        return "Custom Model of Qwen"
