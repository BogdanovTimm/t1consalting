# T1 Consalting

### Pros of this project:

- Allows client to add **options**:
    - **Case-insensitive** (by default) or **Case-sensitive**
    - **Trim all white spaces** or not (by default)
- Both **Unit** and **Integration tests** that covers all possible scenarios
- Custom `ExceptionHandler` for returning information about client's errors
- `Swagger` is used both for creating an **offline documentation** in .pdf format and for **online documentation**
- Extended and clear **javadoc documentation for every method**
- Custom `ApiException` for dealing with **domain-based exceptions**
- **Saves logs** in `charcounter.log` and **creates archives** of them
- `Spring Validaton` is used to strict client's Http-Requests
- **Docker**-image created
- **Docker**-compose created
- `dev` and `prod` environments is used to split application version for development and for production
- `lombock` is used to make code more clear
- Cool T1 Consalting logo included B-)

# Docker-image

You can pull docker-image with:
`docker pull bogdanovtimm/charcounter`
