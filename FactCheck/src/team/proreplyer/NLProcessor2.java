package team.proreplyer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import com.google.gson.Gson;

public class NLProcessor2 {
	public String ip;
	public int port;
	public Socket socket;// = new Socket("1.245.47.141",1115);
	public OutputStream out;
	public SocketOutputStream socketOut;
	public InputStream in;
	public SocketInputStream socketInput;
	public static String input;

	public static void main(String[] args) throws Exception {
		
		System.out.println("Start!");

		int cnt = 0;

		NLProcessor2 etir = new NLProcessor2();
		etir.connect("218.234.158.115", 5555);

		input = "최근 한달간 한국콜마의 상장주식수 대비 거래량을 비교해보니 일별 매매회전율이 0.65%로 집계됐다.";
		etir.send(input);
		String etriR = etir.recieve();
		String r = "";
		System.out.println(etriR);

		ResultETRI re = null;

		try {
			Gson gson = new Gson();
			re = gson.fromJson(etriR, ResultETRI.class);

			List<Sentence> sentences = re.getSentenc();

			for (int i = 0; i < sentences.size(); i++) {

				List<Morp> nes = sentences.get(i).getMorp();

				for (int j = 0; j < nes.size(); j++) {

					Morp tk = nes.get(j);
					r += " " + tk.getLemma();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			r = "!@#$";
		}

		System.out.println(input);
		System.out.println(r);

		etir.close();
		cnt++;
		System.out.println(cnt);
		Thread.sleep(100);
		System.out.println("End!");
		
		//NLP("우리는 배가 고파서 저녁에 보쌈을 먹었다.");
	}

	public void connect(String ip, int port) {

		try {
			socket = new Socket(ip, port);

			out = socket.getOutputStream();
			socketOut = new SocketOutputStream(out);

			in = socket.getInputStream();
			socketInput = new SocketInputStream(in);

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void send(String url) {
		try {
			socketOut.writeString(url);
			socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public String recieve() {
		String r = "";
		try {
			r = socketInput.readString();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return r;
	}

	public void close() {
		try {
			socket.close();
			socketOut.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		socket = null;
		socketOut = null;
		in = null;
		socketInput = null;
	}

	public class ResultETRI {
		public String getDoc_id() {
			return doc_id;
		}

		public void setDoc_id(String doc_id) {
			this.doc_id = doc_id;
		}

		public String getDCT() {
			return DCT;
		}

		public void setDCT(String dCT) {
			DCT = dCT;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public int getCategory_weight() {
			return category_weight;
		}

		public void setCategory_weight(int category_weight) {
			this.category_weight = category_weight;
		}

		public Title getTitle() {
			return title;
		}

		public void setTitle(Title title) {
			this.title = title;
		}

		public MetaInfo getMetaInfo() {
			return metaInfo;
		}

		public void setMetaInfo(MetaInfo metaInfo) {
			this.metaInfo = metaInfo;
		}

		public List<Sentence> getSentenc() {
			return sentence;
		}

		public void setSentenc(List<Sentence> sentence) {
			this.sentence = sentence;
		}

		public List<Entity> getEntity() {
			return entity;
		}

		public void setEntity(List<Entity> entity) {
			this.entity = entity;
		}

		public String doc_id;
		public String DCT;
		public String category;
		public int category_weight;
		public Title title;
		public MetaInfo metaInfo;
		public List<Sentence> sentence;
		public List<Entity> entity;
	}

	public class Title {
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getNE() {
			return NE;
		}

		public void setNE(String nE) {
			NE = nE;
		}

		public String text;
		public String NE;
	}

	public class MetaInfo {

	}

	public class Sentence {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getReserve_str() {
			return reserve_str;
		}

		public void setReserve_str(String reserve_str) {
			this.reserve_str = reserve_str;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public List<Morp> getMorp() {
			return morp;
		}

		public void setMorp(List<Morp> morp) {
			this.morp = morp;
		}

		public List<Morp_eval> getMorp_eval() {
			return morp_eval;
		}

		public void setMorp_eval(List<Morp_eval> morp_eval) {
			this.morp_eval = morp_eval;
		}

		public List<WSD> getWSD() {
			return WSD;
		}

		public void setWSD(List<WSD> wSD) {
			WSD = wSD;
		}

		public List<Word> getWord() {
			return word;
		}

		public void setWord(List<Word> word) {
			this.word = word;
		}

		public List<NE> getNE() {
			return NE;
		}

		public void setNE(List<NE> nE) {
			NE = nE;
		}

		public List<Chunk> getChunk() {
			return chunk;
		}

		public void setChunk(List<Chunk> chunk) {
			this.chunk = chunk;
		}

		public List<Dependency> getDependency() {
			return dependency;
		}

		public void setDependency(List<Dependency> dependency) {
			this.dependency = dependency;
		}

		public List<Phrase_dependency> getPhrase_dependency() {
			return phrase_dependency;
		}

		public void setPhrase_dependency(List<Phrase_dependency> phrase_dependency) {
			this.phrase_dependency = phrase_dependency;
		}

		public List<SRL> getSRL() {
			return SRL;
		}

		public void setSRL(List<SRL> sRL) {
			SRL = sRL;
		}

		public List<Relation> getRelation() {
			return relation;
		}

		public void setRelation(List<Relation> relation) {
			this.relation = relation;
		}

		public List<SA> getSA() {
			return SA;
		}

		public void setSA(List<SA> sA) {
			SA = sA;
		}

		public List<ZA> getZA() {
			return ZA;
		}

		public void setZA(List<ZA> zA) {
			ZA = zA;
		}

		int id;
		public String reserve_str;
		public String text;
		public List<Morp> morp;
		public List<Morp_eval> morp_eval;
		public List<WSD> WSD;
		public List<Word> word;
		public List<NE> NE;
		public List<Chunk> chunk;
		public List<Dependency> dependency;
		public List<Phrase_dependency> phrase_dependency;
		public List<SRL> SRL;
		public List<Relation> relation;
		public List<SA> SA;
		public List<ZA> ZA;
		public double majorP = 0;
	}

	public class Morp {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLemma() {
			return lemma;
		}

		public void setLemma(String lemma) {
			this.lemma = lemma;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public int id;
		public String lemma;
		public String type;
		public int position;
		public double weight;
	}

	public class Morp_eval {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public int getWord_id() {
			return word_id;
		}

		public void setWord_id(int word_id) {
			this.word_id = word_id;
		}

		public int getM_begin() {
			return m_begin;
		}

		public void setM_begin(int m_begin) {
			this.m_begin = m_begin;
		}

		public int getM_end() {
			return m_end;
		}

		public void setM_end(int m_end) {
			this.m_end = m_end;
		}

		public int id;
		public String result;
		public String target;
		public int word_id;
		public int m_begin;
		public int m_end;
	}

	public class WSD {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getScode() {
			return scode;
		}

		public void setScode(String scode) {
			this.scode = scode;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public int getBegin() {
			return begin;
		}

		public void setBegin(int begin) {
			this.begin = begin;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public int id;
		public String text;
		public String type;
		public String scode;
		public double weight;
		public int position;
		public int begin;
		public int end;
	}

	public class Word {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getBegin() {
			return begin;
		}

		public void setBegin(int begin) {
			this.begin = begin;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public int id;
		public String text;
		public String type;
		public int begin;
		public int end;
	}

	public class NE {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getBegin() {
			return begin;
		}

		public void setBegin(int begin) {
			this.begin = begin;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public int getCommon_noun() {
			return common_noun;
		}

		public void setCommon_noun(int common_noun) {
			this.common_noun = common_noun;
		}

		public int id;
		public String text;
		public String type;
		public int begin;
		public int end;
		public double weight;
		public int common_noun;
	}

	public class Chunk {

	}

	public class Dependency {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getHead() {
			return head;
		}

		public void setHead(int head) {
			this.head = head;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public List<Integer> getMod() {
			return mod;
		}

		public void setMod(List<Integer> mod) {
			this.mod = mod;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public int id;
		public String text;
		public int head;
		public String label;
		public List<Integer> mod;
		public double weight;

		public boolean isNe = false;
		public String neType = "";

		public boolean isGroupWord = false;
		public String groupWord = "";
	}

	public class Phrase_dependency {
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getBegin() {
			return begin;
		}

		public void setBegin(int begin) {
			this.begin = begin;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public int getKey_begin() {
			return key_begin;
		}

		public void setKey_begin(int key_begin) {
			this.key_begin = key_begin;
		}

		public int getHead_phrase() {
			return head_phrase;
		}

		public void setHead_phrase(int head_phrase) {
			this.head_phrase = head_phrase;
		}

		public List<Integer> getSub_phrase() {
			return sub_phrase;
		}

		public void setSub_phrase(List<Integer> sub_phrase) {
			this.sub_phrase = sub_phrase;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public List<Element> getElement() {
			return element;
		}

		public void setElement(List<Element> element) {
			this.element = element;
		}

		public int id;
		public String label;
		public String text;
		public int begin;
		public int end;
		public int key_begin;
		public int head_phrase;
		public List<Integer> sub_phrase;
		public double weight;
		public List<Element> element;
	}

	public class Element {

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public int getBegin() {
			return begin;
		}

		public void setBegin(int begin) {
			this.begin = begin;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public String getNe_type() {
			return ne_type;
		}

		public void setNe_type(String ne_type) {
			this.ne_type = ne_type;
		}

		public String text;
		public String label;
		public int begin;
		public int end;
		public String ne_type;

	}

	public class SRL {
		public String getVerb() {
			return verb;
		}

		public void setVerb(String verb) {
			this.verb = verb;
		}

		public int getSense() {
			return sense;
		}

		public void setSense(int sense) {
			this.sense = sense;
		}

		public int getWord_id() {
			return word_id;
		}

		public void setWord_id(int word_id) {
			this.word_id = word_id;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public List<Argument> getArgument() {
			return argument;
		}

		public void setArgument(List<Argument> argument) {
			this.argument = argument;
		}

		public String verb;
		public int sense;
		public int word_id;
		public double weight;

		public List<Argument> argument;
	}

	public class Argument {
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getWord_id() {
			return word_id;
		}

		public void setWord_id(int word_id) {
			this.word_id = word_id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public String type;
		public int word_id;
		public String text;
		public double weight;
	}

	public class Relation {

	}

	public class SA {

	}

	public class ZA {

	}

	public class Entity {

	}

	public String processInput(String input) {
		System.out.println("Start!");

		NLProcessor2 etir = new NLProcessor2();
		etir.connect("218.234.158.115", 5555);

		etir.send(input);
		String etriR = etir.recieve();
		String r = "";
		System.out.println(etriR);

		ResultETRI re = null;

		try {
			Gson gson = new Gson();
			re = gson.fromJson(etriR, ResultETRI.class);

			List<Sentence> sentences = re.getSentenc();

			for (int i = 0; i < sentences.size(); i++) {

				List<Morp> nes = sentences.get(i).getMorp();

				for (int j = 0; j < nes.size(); j++) {

					Morp tk = nes.get(j);
					r += " " + tk.getLemma();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			r = "!@#$";
		}

		System.out.println(input);
		System.out.println(r);

		etir.close();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("End!");

		return r;
	}
	
	static public String NLP(String input) {
		System.out.println("NLP Start!");

		NLProcessor2 nlprocessor = new NLProcessor2();
		nlprocessor.connect("218.234.158.115", 5555);

		nlprocessor.send(input);
		String nlprocessorR = nlprocessor.recieve();
		String r = "";
		System.out.println(nlprocessorR);

		ResultETRI re = null;

		try {
			Gson gson = new Gson();
			re = gson.fromJson(nlprocessorR, ResultETRI.class);

			List<Sentence> sentences = re.getSentenc();

			for (int i = 0; i < sentences.size(); i++) {

				List<Morp> nes = sentences.get(i).getMorp();

				for (int j = 0; j < nes.size(); j++) {

					Morp tk = nes.get(j);
					r += " " + tk.getLemma();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			r = "!@#$";
		}

		System.out.println(input);
		System.out.println(r);

		nlprocessor.close();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("End!");

		return r;
	}
}
