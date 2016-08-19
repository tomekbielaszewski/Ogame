/* eslint "no-unused-vars": 0  */
var matchers = {
    toHaveDependencies: toHaveDependencies
};

function toHaveDependencies() {
    'use strict';

    return {
        compare: compareDependencies
    };
}

function compareDependencies(dependencies, expected) {
    'use strict';

    if (!dependencies || !dependencies.length) {
        return thereIsNoDependenciesError();
    }
    return getResult(dependencies, expected);
}

function thereIsNoDependenciesError() {
    'use strict';

    return {
        pass: false,
        message: 'There is no dependencies'
    };
}

function getResult(dependencies, expected) {
    'use strict';

    var expectedDependenciesLength = 0,
        dependenciesLength = dependencies.length,
        difference = 0,
        moduleName;

    if (expected) {
        expectedDependenciesLength = expected.length;
        difference = dependenciesLength - expectedDependenciesLength;

        /* eslint "semi-spacing": 0 */
        for (var i = expectedDependenciesLength; i--;) {
            moduleName = expected[i];

            if (!contains(dependencies, moduleName)) {
                return moduleExpectedError(moduleName);
            }
        }
    }

    if (difference) {
        return thereIsMoreDependencies(expectedDependenciesLength, difference);
    }
    return expectationMatch(dependenciesLength);
}

function contains(actual, moduleName) {
    'use strict';

    return actual.indexOf(moduleName) > -1;
}

function moduleExpectedError(moduleName) {
    'use strict';

    return {
        pass: false,
        message: 'Expected ' + moduleName + ' to be in module dependencies'
    };
}

function thereIsMoreDependencies(expectedDependenciesLength, difference) {
    'use strict';

    return {
        pass: false,
        message: 'Expected ' + expectedDependenciesLength + ' dependencies, ' +
            'but there is ' + difference + ' more'
    };
}

function expectationMatch(difference) {
    'use strict';

    return {
        pass: true,
        message: 'There are ' + difference + ' dependencies'
    };
}
