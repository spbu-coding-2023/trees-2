# forest-group

[![Kotlin][kotlin_img]][kotlin_releases_url]
[![License][license_img]][repo_license_url]
![Coverage](.github/badges/jacoco.svg)


`forest-group` is a library that lets you easily create and use [Binary search trees](https://en.wikipedia.org/wiki/Binary_search_tree), [AVL trees](https://en.wikipedia.org/wiki/AVL_tree) and [Red-black trees](https://en.wikipedia.org/wiki/Red%E2%80%93black_tree) in your applications!



## Table of contents
- [About the project](#about-the-project)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)



## About The Project
Tree data structure is well-known for its average logarithmic operations time. However, there are a lot of tree implementations that are strict to data types that can be stored.
There is no problem - *just use keys to store anything you want!*



## Usage
To create a tree, pass the key type and your stored data type to a generic. *Note that your key should implement Comparable type.*

```
val myTree = AVLTree<Int, String>()
```

You now can simply insert and replace values in your tree:

```
myTree.insert(keyA, "Something important")
val oldValue = myTree.insert(keyA, "Something more important")
```

You can also search for values and delete values from tree by keys:

```
val myValue = myTree.search(myKey)
myTree.delete(myKey)
```



## Contributing
If you have found a bug, or want to propose some useful feature for our project, please do the following:
1. Fork the Project
2. Create your Feature Branch (git checkout -b feature/MyFeature)
3. Commit your Changes (git commit -m 'add some Feature')
4. Push to the Branch (git push origin feature/MyFeature)
5. Open a Pull Request



## License
Distributed under the MIT License. See LICENSE.md for more information.



<!-- Image links -->

[license_img]: https://img.shields.io/badge/license-MIT-green
[kotlin_img]: https://img.shields.io/badge/kotlin-magenta

<!-- Repo links -->

[repo_license_url]: https://github.com/spbu-coding-2023/trees-2/blob/main/LICENSE.md

<!-- Kotlin links -->

[kotlin_releases_url]: https://kotlinlang.org/docs/releases.html#release-details
