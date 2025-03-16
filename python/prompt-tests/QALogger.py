from langchain.callbacks.base import BaseCallbackHandler


# 自定义回调处理器
class QALogger(BaseCallbackHandler):
    def on_llm_start(self, serialized, prompts, **kwargs):
        # Log the input (question)
        for prompt in prompts:
            print(f"问: {prompt}")

    def on_llm_end(self, response, **kwargs):
        # Log the output (answer)
        if hasattr(response, 'generations') and response.generations:
            for generation in response.generations:
                for gen in generation:
                    print(f"答: {gen.text}")
