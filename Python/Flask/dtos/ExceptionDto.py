from dataclasses import dataclass

@dataclass(frozen=True)
class ExceptionDto:
    msg : str
