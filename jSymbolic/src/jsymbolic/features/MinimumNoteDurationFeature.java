/*
 * MinimumNoteDurationFeature.java
 * Version 1.2
 *
 * Last modified on April 11, 2010.
 * McGill University
 */

package jsymbolic.features;

import java.util.LinkedList;
import javax.sound.midi.*;
import ace.datatypes.FeatureDefinition;
import jsymbolic.processing.MIDIIntermediateRepresentations;


/**
 * A feature exractor that finds the duration of the shortest note (in seconds).
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class MinimumNoteDurationFeature
	extends MIDIFeatureExtractor
{
	/* CONSTRUCTOR ********************************************************/
	
	
	/**
	 * Basic constructor that sets the definition and dependencies (and 
         * their offsets) of this feature.
	 */
	public MinimumNoteDurationFeature()
	{
		String name = "Minimum Note Duration";
		String description = "Duration of the shortest note (in seconds).";
		boolean is_sequential = true;
		int dimensions = 1;
		definition = new FeatureDefinition( name,
		                                    description,
		                                    is_sequential,
		                                    dimensions );

		dependencies = null;
		
		offsets = null;
	}


	/* PUBLIC METHODS *****************************************************/

	
	/**
      * Extracts this feature from the given MIDI sequence given the other
      * feature values.
      *
      * <p>In the case of this feature, the other_feature_values parameters
      * are ignored.
      *
      * @param sequence			The MIDI sequence to extract the feature
      *                                 from.
      * @param sequence_info		Additional data about the MIDI sequence.
      * @param other_feature_values	The values of other features that are
      *					needed to calculate this value. The
      *					order and offsets of these features
      *					must be the same as those returned by
      *					this class's getDependencies and
      *					getDependencyOffsets methods
      *                                 respectively. The first indice indicates
      *                                 the feature/window and the second
      *                                 indicates the value.
      * @return				The extracted feature value(s).
      * @throws Exception		Throws an informative exception if the
      *					feature cannot be calculated.
	 */
	public double[] extractFeature( Sequence sequence,
                                    MIDIIntermediateRepresentations sequence_info,
	                                double[][] other_feature_values )
		throws Exception
	{
		double value;
		if (sequence_info != null)
		{
			// Put durations in an array
			Object[] durations_obj = sequence_info.note_durations.toArray();
			double[] durations = new double[durations_obj.length];
			for (int i = 0; i < durations.length; i++)
				durations[i] = ((Double) durations_obj[i]).doubleValue(); 

			// Calculate value
			int index = mckay.utilities.staticlibraries.MathAndStatsMethods.getIndexOfSmallest(durations);
			value = durations[index];
		}
		else value = -1.0;

		double[] result = new double[1];
		result[0] = value;
		return result;
	}
}