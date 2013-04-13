package org.jbehave.core.configuration;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.UsingStepsOrderTest.A;
import org.jbehave.core.configuration.UsingStepsOrderTest.B;
import org.jbehave.core.configuration.UsingStepsOrderTest.C;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.AnnotatedEmbedderRunner;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AnnotatedEmbedderRunner.class)
// @UsingPaths(searchIn = "src/test/resources", includes =
// { "org/jbehave/core/configuration/using_steps_order.story" })
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = false, ignoreFailureInView = true)
@UsingSteps(instances =
{ A.class, B.class, C.class, UsingStepsOrderTest.class })
@Configure(storyReporterBuilder = UsingStepsOrderTest.class)
public class UsingStepsOrderTest extends StoryReporterBuilder implements
		Embeddable
{
	public UsingStepsOrderTest()
	{
		withFormats(Format.CONSOLE, Format.STATS);
	}

	private Embedder embedder;

	public void useEmbedder(Embedder embedder)
	{
		this.embedder = embedder;
	}

	@Test
	public void run()
	{
		embedder.runStoriesAsPaths(Arrays
				.asList("org/jbehave/core/configuration/using_steps_order.story"));
	}

	private static final List<String> ORDER = new LinkedList<String>();

	@AfterScenario
	public void clearOrder()
	{
		ORDER.clear();
	}

	public static class A
	{
		@BeforeScenario
		public void add()
		{
			ORDER.add(getClass().getSimpleName());
		}
	}

	public static class B
	{
		@BeforeScenario
		public void add()
		{
			ORDER.add(getClass().getSimpleName());
		}
	}

	public static class C
	{
		@BeforeScenario
		public void add()
		{
			ORDER.add(getClass().getSimpleName());
		}
	}

	@When("running @BeforeScenario")
	public void noop()
	{
	}

	@Then("$left should be called before $right")
	public void assertOrder(String left, String right)
	{
		int leftIndex = ORDER.indexOf(left);
		int rightIndex = ORDER.indexOf(right);

		assertTrue(left + " was not called before " + right,
				leftIndex < rightIndex);
	}
}
