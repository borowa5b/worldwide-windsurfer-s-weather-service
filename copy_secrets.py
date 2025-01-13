import os

for name, value in os.environ.items():
    valueToReplace = f'#{{{name}}}'
    with open('src/main/resources/application.properties', 'r') as file:
        content = file.read()
    with open('src/main/resources/application.properties', 'w') as file:
        file.write(content.replace(valueToReplace, value))