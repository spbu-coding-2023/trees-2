# forest-group

[![Kotlin 1.9.23][kotlin_img]][kotlin_releases_url]
[![Tests passing][tests_passing_img]][tests_workflow_url]
[![Code Coverage][code_coverage_badge_img]][code_coverage_workflow_url]
[![License][license_img]][repo_license_url]

`forest-group` is a library that lets you easily create and use [Binary search trees](https://en.wikipedia.org/wiki/Binary_search_tree), [AVL trees](https://en.wikipedia.org/wiki/AVL_tree) and [Red-black trees](https://en.wikipedia.org/wiki/Red%E2%80%93black_tree) in your applications!

## Table of contents

- [About the project](#about-the-project)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Authors](#authors)

## About The Project

Trees are well-known for their speed and efficiency.

But there are lots of tree implementations that are strict to data types that can be stored.

The solution is easy - *just use keys to store anything you want!*

## Usage

To create a tree, pass the key type and your stored data type to a generic. *Note that your key should implement Comparable type.*

```kotlin
val myBSTree = BSTree<Int, String>()

val myAVLTree = AVLTree<Char, Long>()

val myRBTree = RBTree<String, Float>()
```

You now can simply insert and replace values in your tree:

```kotlin
val myKey = 10

myBSTree.insert(myKey, "Something important")

val replacedValue = myBSTree.insert(myKey, "Something more important")
```

You can also search for values and delete values from tree by keys:

```kotlin
val myValue1 = myBSTree.search(myKey)

val myValue2 = myBSTree.delete(myKey)

println(myValue1 == myValue2) // true
```

All trees are iterable by Pair(key, value), so they can be used in a for loop.

Iterator implements inorder traversal (every next key is greater than the previous).

```kotlin
for ((key, value) in myRBTree) {
    keysList.add(key)
    valuesList.add(value)
}

myRBTree.forEach { println(it) } // prints every pair
```

## Contributing

If you have found a bug, or want to propose some useful feature for our project, please firstly read our [Contribution Rules][contribute_rules_url] and
do the following:
1. Fork the Project
2. Create your Feature Branch (git checkout -b feature/my-feature)
3. Commit your Changes (git commit -m 'add some feature')
4. Push to the Branch (git push origin feature/my-feature)
5. Open a Pull Request

## License

Distributed under the [MIT License][repo_license_url].

## Authors

- [Shakirov Karim](https://github.com/kar1mgh)
- [Vlasenco Daniel](https://github.com/spisladqo)
- [Gavrilenko Mike](https://github.com/qrutyy)

_______________________________

[*Java gnomik*][java_gnomik_url]

<!-- Image links -->

[kotlin_img]: https://img.shields.io/badge/Kotlin-%201.9.23-magenta
[tests_passing_img]: https://img.shields.io/badge/tests-Passing-green
[license_img]: https://img.shields.io/badge/license-MIT-blue
[code_coverage_badge_img]: https://raw.githubusercontent.com/spbu-coding-2023/trees-2/main/.github/badges/jacoco.svg

<!-- Inner Links -->

[tests_workflow_url]: https://github.com/spbu-coding-2023/trees-2/actions/workflows/test.yml
[repo_license_url]: https://github.com/spbu-coding-2023/trees-2/blob/main/LICENSE.md
[contribute_rules_url]: https://github.com/spbu-coding-2023/trees-2/blob/main/CONTRIBUTING.md
[code_coverage_workflow_url]: https://github.com/spbu-coding-2023/trees-2/actions/workflows/codecoverage.yml

<!-- Outer Links -->

[kotlin_releases_url]: https://kotlinlang.org/docs/releases.html#release-details
[java_gnomik_url]: https://ibb.co/54hJVd2
