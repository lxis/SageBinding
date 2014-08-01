package com.sage.binding.mvvm;

import java.util.ArrayList;

import com.sage.binding.mvvm.model.TagBindingAnalysisResult;

public class BindingAnalyst {
	Boolean isFinish = false;

	Integer pos = 0;

	Integer currentPos = 0;

	ArrayList<TagBindingAnalysisResult> list = new ArrayList<TagBindingAnalysisResult>();

	public ArrayList<TagBindingAnalysisResult> loadBindingParamText(String tag) {
		while (!isFinish) {
			TagBindingAnalysisResult tagInfo = new TagBindingAnalysisResult();
			char[] methodName = new char[32];
			char[] singnal = tag.toCharArray();

			currentPos = pos;
			while (true) {
				char currentChar = singnal[pos];
				if (currentChar != '=' || singnal[pos + 1] != '>')
					methodName[pos++ - currentPos + 3] = currentChar;// Q:3
				else
					break;
			}

			int methodLength = pos - currentPos + 3;// Q:3
			pos += 2;
			currentPos = pos;

			// �?始搞field
			analysisFields(singnal, tagInfo);
			// field搞完

			methodLength = addOmitPart(methodName, methodLength);

			tagInfo.MethodNameString = new String(methodName, 0, methodLength);

			list.add(tagInfo);
		}
		return list;
	}

	private int addOmitPart(char[] methodName, int methodLength) {
		if (Character.isUpperCase(methodName[3])) {
			methodName[0] = 's';// Q:鍐欐�?
			methodName[1] = 'e';
			methodName[2] = 't';
		}

		if (methodName[3] == 'O' && methodName[4] == 'n') {
			methodName[methodLength + 0] = 'L';
			methodName[methodLength + 1] = 'i';
			methodName[methodLength + 2] = 's';
			methodName[methodLength + 3] = 't';
			methodName[methodLength + 4] = 'e';
			methodName[methodLength + 5] = 'n';
			methodName[methodLength + 6] = 'e';
			methodName[methodLength + 7] = 'r';
			methodLength += 8;
		}
		return methodLength;
	}

	private void analysisFields(char[] singnal, TagBindingAnalysisResult tagInfo) {
		ArrayList<char[]> fieldNames = new ArrayList<char[]>();
		ArrayList<Integer> fieldLengths = new ArrayList<Integer>();
		boolean isContinueCurrentField = true;
		while (!isFinish && isContinueCurrentField) {
			char[] fieldName = new char[32];
			fieldNames.add(fieldName);
			int currentPos2 = pos;// 这个比较�?
			while (true) {
				fieldName[pos - currentPos2] = singnal[pos];
				pos++;
				if (pos == singnal.length) {
					isFinish = true;
					fieldLengths.add(pos - currentPos2);
					break;
				}
				if (singnal[pos] == ',') {
					isContinueCurrentField = false;
					fieldLengths.add(pos - currentPos2);
					pos++;
					break;
				}
				if (singnal[pos] == '.') {
					fieldLengths.add(pos - currentPos2);
					pos++;
					break;
				}
			}
		}

		for (int i = 0; i < fieldNames.size(); i++)
			tagInfo.FieldNameStrings.add(new String(fieldNames.get(i), 0,
					fieldLengths.get(i)));

		tagInfo.FieldName = tagInfo.FieldNameStrings
				.get(tagInfo.FieldNameStrings.size() - 1);
	}
}
