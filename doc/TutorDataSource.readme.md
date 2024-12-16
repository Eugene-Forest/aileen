# TutorDataSource

## `BeanDefinitionRegistryPostProcessor`

BeanDefinitionRegistryPostProcessor 是Spring框架提供的一个扩展接口，它允许在Spring容器加载bean定义信息之前，添加额外的bean定义或者对已有的bean定义进行修改。
这个接口继承自 BeanFactoryPostProcessor，但提供了更早的介入点，即在标准的BeanFactoryPostProcessor检测开始之前。

要使用 BeanDefinitionRegistryPostProcessor，你需要创建一个类实现这个接口，并重写其中的 postProcessBeanDefinitionRegistry 方法。
在这个方法中，你可以通过 BeanDefinitionRegistry 参数来注册新的bean定义或者操作现有的bean定义。

### 实现接口

```java

//@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 在这里注册新的bean定义
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(MyBean.class);
        registry.registerBeanDefinition("myBean", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 这个方法用于在BeanFactory标准初始化之后进行自定义修改
    }
}

```

### 激活配置

要让你的 BeanDefinitionRegistryPostProcessor 生效，你需要将其注册到Spring容器中。有两种常见的方法可以做到这一点：

* 使用 @Configuration 和 @Bean 注解：

```java

@Configuration
public class MyConfig {
    @Bean
    public MyBeanDefinitionRegistryPostProcessor myBeanDefinitionRegistryPostProcessor() {
        return new MyBeanDefinitionRegistryPostProcessor();
    }
}
```

* 使用 @Component 注解直接将其声明为组件：

```java

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
// 实现方法
}
```
当你使用 @ComponentScan 注解指定组件扫描路径时，Spring会自动检测到使用 @Component 注解的类，并将其注册到容器中。

### 使用场景

BeanDefinitionRegistryPostProcessor 的一个典型使用场景是在Spring Boot中动态注册bean定义。
例如，你可能想根据某些条件动态地注册或者不注册某些bean，或者你想在运行时动态地改变bean的配置。
这个接口也常被用于Spring框架内部或者第三方库中，以编程的方式注册bean，而不是通过标准的注解或XML配置。

### 执行时机

BeanDefinitionRegistryPostProcessor 的 postProcessBeanDefinitionRegistry 方法在所有bean定义将要被加载到IOC容器中，
但bean实例还未被创建之前执行。 这意味着你可以在bean定义加载过程中，但在bean实例化之前，对bean定义进行操作。

### 总结

通过实现 BeanDefinitionRegistryPostProcessor 接口，你可以在Spring容器的bean定义加载阶段进行自定义操作，
这为Spring应用的扩展提供了强大的灵活性。无论是通过注解还是编程的方式，都可以轻松地将自定义的后置处理器集成到Spring生命周期中，从而实现复杂的业务需求。


## `ApplicationContextAware`

ApplicationContextAware 是Spring框架中的一个接口，它允许Bean在运行时获取Spring容器的上下文（ApplicationContext）。
这样，Bean就可以访问容器中的其他Bean、资源、环境属性等。这个接口在Spring Boot中的应用非常广泛，尤其是在需要Bean与Spring容器交互的场景中。

### ApplicationContextAware的作用

实现了ApplicationContextAware接口的Bean可以在运行时执行多种操作，例如：

* 获取Spring容器上下文：Bean可以获取ApplicationContext的实例，进而与Spring容器进行交互。

* 访问其他Bean：Bean可以通过ApplicationContext访问其他Bean的实例，这对于依赖注入和方法调用非常有用。

* 执行特定逻辑：在获取ApplicationContext之后，Bean可以执行初始化任务或其他特定逻辑。


### 应用场景

ApplicationContextAware可以应用于多种场景，包括但不限于：

* 访问其他Bean：例如，一个Bean可能需要调用另一个Bean的方法或访问其属性。

* 执行初始化任务：Bean可以在获取ApplicationContext后执行初始化代码，如设置属性或启动后台进程。

* 获取容器信息：Bean可以获取容器的详细信息，如Bean定义、环境属性和配置信息。

```java

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContextAware implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return context.getBean(beanName, beanClass);
    }
}
```

ApplicationContextAware 是Spring Boot中一个非常有用的工具，它提供了一种机制，让Bean在运行时与Spring容器进行交互。
这个接口的实现可以帮助开发者在Bean中执行复杂的操作，同时保持代码的清晰和组织。


## ApplicationRunner

ApplicationRunner 是 Spring Boot 2.x 版本中引入的接口，用于在 Spring Boot 应用程序启动完成后，立即执行一些特定的逻辑。

它的设计灵感来源于 CommandLineRunner，后者是 Spring Boot 1.x 版本中用于类似目的的接口。两者的主要区别在于，ApplicationRunner 提供了对应用启动参数的更高级别封装，使开发者可以更方便地处理启动参数。

虽然 ApplicationRunner 和 CommandLineRunner 的目的都是在应用程序启动完成后执行代码，但二者之间的主要区别在于它们对输入参数的处理方式。
CommandLineRunner 直接接收一个 String[] 数组作为参数，这意味着开发者需要自己解析和处理这些参数。
而 ApplicationRunner 则接收一个 ApplicationArguments 对象，该对象封装了启动参数，并提供了一些有用的方法来方便地处理这些参数。

> CommandLineRunner 接口的实现

```java

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            System.out.println("Argument: " + arg);
        }
    }
}
```

> ApplicationRunner 接口的实现

```java

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("myOption")) {
            System.out.println("Option 'myOption' is present.");
        }
        for (String nonOptionArg : args.getNonOptionArgs()) {
            System.out.println("Non-option argument: " + nonOptionArg);
        }
    }
}
```

### 应用场景

ApplicationRunner 的核心功能是在 Spring Boot 应用程序启动完成后立即执行代码。
这对于那些需要在应用启动完成后进行额外配置或初始化的场景非常有用。例如，以下是一些常见的使用场景：

* 加载配置数据：在应用启动时，从数据库或文件中加载一些需要在整个应用生命周期内使用的配置信息。
* 检查系统环境：在应用启动后检查系统环境是否满足运行条件，例如检查某些外部服务是否可用。
* 启动后台任务：在应用启动后，立即启动某些后台任务，如数据同步、日志清理等。


### ApplicationRunner 的实际应用经验

在实际应用中，ApplicationRunner 的使用可能会遇到一些挑战，例如启动顺序、依赖注入等问题。以下是一些最佳实践和经验分享：

* 控制启动顺序：在某些情况下，可能需要确保 ApplicationRunner 在其他初始化代码之后执行。可以使用 `@Order` 注解来控制不同 ApplicationRunner  实现的执行顺序。
* 避免过度依赖：虽然 ApplicationRunner 非常强大，但不要将所有初始化逻辑都放在其中，这会使得代码难以维护。建议将不同的初始化任务分开处理，并通过合理的架构设计来简化依赖关系。
* 合理使用日志：在 ApplicationRunner 中执行的任务通常非常重要，因此建议在这些任务中添加详细的日志记录，以便在应用启动时能够清晰地了解各个步骤的执行情况。

## @ConditionalOnMissingBean

在SpringBoot中，@ConditionalOnMissingBean注解是一个条件注解，它确保当Spring容器中不存在某个Bean时，才会创建一个新的Bean。
这个注解通常用于自动配置类中，以避免Bean的重复注册，确保Spring容器中只有一个特定类型的Bean实例。

### 应用场景

@ConditionalOnMissingBean注解通常用于以下场景：

* 当你有一个接口和多个实现类时，如果你只希望有一个实现类被Spring容器管理，可以在其他实现类上使用@ConditionalOnMissingBean注解。

* 在自动配置类中，当你提供一个默认的Bean实现，但允许用户根据业务需求自定义自己的Bean时，可以在默认实现上添加@ConditionalOnMissingBean注解。这样，如果用户定义了自己的Bean，Spring容器将不会加载默认的Bean实现。

使用@ConditionalOnMissingBean注解时需要注意以下几点：

* 应该只在自动配置类中使用此注解。虽然在其他类中使用也不会报错，但不推荐这样做。

* 注解仅能匹配当前应用上下文中已经管理的Bean定义。

* 如果候选Bean是由其他配置类创建的，需要使用@AutoConfigureBefore或@AutoConfigureOrder注解来控制配置类的加载顺序，确保@ConditionalOnMissingBean注解在其后运行。

> 扩展 ： 自动配置类
> //TODO
> 


## AbstractRoutingDataSource

AbstractRoutingDataSource 是Spring框架提供的一个抽象类，它允许开发者实现动态数据源切换，特别适用于多租户系统中，每个租户拥有自己的数据库实例。
这个类的核心是 determineCurrentLookupKey() 方法，它决定了当前操作应该使用哪个数据源。

